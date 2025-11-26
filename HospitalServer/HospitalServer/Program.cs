using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Data.SQLite;
using System.Text.Json;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace HospitalServer
{
    // ЄДИНИЙ КЛАС Patient — тільки тут і ніде більше!
    public class Patient
    {
        public int Id { get; set; }
        public string FullName { get; set; } = "";
        public DateTime BirthDate { get; set; }
        public string Diagnosis { get; set; } = "";
        public string Ward { get; set; } = "";
    }

    class Program
    {
        private static readonly string dbPath = "Hospital.db";
        private static readonly string connectionString = $"Data Source={dbPath};Version=3;";

        private static readonly JsonSerializerOptions jsonOptions = new()
        {
            PropertyNamingPolicy = null,
            WriteIndented = false
        };

        static void Main()
        {
            InitializeDatabase();

            TcpListener server = new(IPAddress.Parse("127.0.0.1"), 12345);
            server.Start();
            Console.WriteLine("Сервер запущено на 127.0.0.1:12345");

            while (true)
            {
                TcpClient client = server.AcceptTcpClient();
                _ = Task.Run(() => HandleClient(client));
            }
        }

        private static void InitializeDatabase()
        {
            // Якщо база вже є — залишаємо, але додаємо дані тільки один раз
            if (!System.IO.File.Exists(dbPath))
                SQLiteConnection.CreateFile(dbPath);

            using var conn = new SQLiteConnection(connectionString);
            conn.Open();

            // Створюємо таблицю
            string createTable = @"
        CREATE TABLE IF NOT EXISTS Patients (
            Id INTEGER PRIMARY KEY AUTOINCREMENT,
            FullName TEXT NOT NULL,
            BirthDate TEXT NOT NULL,
            Diagnosis TEXT,
            Ward TEXT
        );";
            new SQLiteCommand(createTable, conn).ExecuteNonQuery();

            // 15 пацієнтів для демонстрації (обов’язково для лаб. №4)
            var patients = new[]
            {
        ("Іванов Іван Іванович", "1985-03-15", "ГРВІ", "Терапія 1"),
        ("Петренко Ольга Миколаївна", "1990-07-22", "Пневмонія", "Терапія 2"),
        ("Шевченко Андрій Петрович", "1978-11-30", "Гіпертонічна хвороба", "Кардіологія"),
        ("Коваленко Марія Василівна", "1995-01-10", "Гострий гастрит", "Гастроентерологія"),
        ("Бойко Сергій Григорович", "1988-09-05", "Перелом променевої кістки", "Травматологія"),
        ("Ткаченко Оксана Володимирівна", "1992-04-18", "Мігрень", "Неврологія"),
        ("Савченко Дмитро Олександрович", "1980-12-25", "Цукровий діабет 2 типу", "Ендокринологія"),
        ("Мельник Наталія Ігорівна", "1993-06-14", "Гострий тонзиліт", "Терапія 1"),
        ("Гончаренко Віктор Леонідович", "1975-08-20", "Ревматоїдний артрит", "Ревматологія"),
        ("Лисенко Світлана Анатоліївна", "1987-02-28", "Гострий бронхіт", "Пульмонологія"),
        ("Кравченко Юлія Сергіївна", "1991-10-03", "Гострий середній отит", "ЛОР-відділення"),
        ("Мороз Володимир Іванович", "1983-05-17", "Вірусний гепатит А", "Інфекційне"),
        ("Сидоренко Анна Павлівна", "1994-09-11", "ГРВІ", "Терапія 2"),
        ("Даниленко Роман Васильович", "1986-12-01", "Гострий панкреатит", "Хірургія"),
        ("Захарченко Тетяна Олегівна", "1989-03-27", "Напад мігрені", "Неврологія")
    };

            foreach (var (fullName, birthDate, diagnosis, ward) in patients)
            {
                var cmd = conn.CreateCommand();
                cmd.CommandText = @"
            INSERT OR IGNORE INTO Patients (FullName, BirthDate, Diagnosis, Ward)
            VALUES (@name, @birth, @diag, @ward)";
                cmd.Parameters.AddWithValue("@name", fullName);
                cmd.Parameters.AddWithValue("@birth", birthDate);
                cmd.Parameters.AddWithValue("@diag", diagnosis);
                cmd.Parameters.AddWithValue("@ward", ward);
                cmd.ExecuteNonQuery();
            }

            Console.WriteLine("База даних готова.");
        }

        private static void HandleClient(TcpClient client)
        {
            try
            {
                NetworkStream stream = client.GetStream();

                // Читаємо довжину
                byte[] lenBytes = new byte[4];
                int read = 0;
                while (read < 4)
                {
                    int r = stream.Read(lenBytes, read, 4 - read);
                    if (r <= 0) return;
                    read += r;
                }
                int requestLength = BitConverter.ToInt32(lenBytes, 0);

                // Читаємо запит
                byte[] requestBytes = new byte[requestLength];
                read = 0;
                while (read < requestLength)
                {
                    int r = stream.Read(requestBytes, read, requestLength - read);
                    if (r <= 0) return;
                    read += r;
                }

                string request = Encoding.UTF8.GetString(requestBytes);
                Console.WriteLine("Отримано: " + request);

                string response = ProcessCommand(request);
                byte[] responseBytes = Encoding.UTF8.GetBytes(response);

                // КРИТИЧНО ВАЖЛИВО:
                byte[] lengthPrefix = BitConverter.GetBytes(responseBytes.Length);
                stream.Write(lengthPrefix, 0, 4);
                stream.Write(responseBytes, 0, responseBytes.Length);

                // Примусово виштовхуємо всі дані
                stream.Flush();

                // Додаємо маленьку затримку, щоб клієнт встиг прочитати
                System.Threading.Thread.Sleep(50);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Помилка: " + ex.Message);
            }
            finally
            {
                try { client.Close(); } catch { }
            }
        }

        private static string ProcessCommand(string json)
        {
            try
            {
                using JsonDocument doc = JsonDocument.Parse(json);
                var root = doc.RootElement;

                string cmd = root.GetProperty("command").GetString()!;

                return cmd switch
                {
                    "add" => AddPatient(JsonSerializer.Deserialize<Patient>(root.GetProperty("data").GetRawText(), jsonOptions)!),
                    "get" => JsonSerializer.Serialize(GetPatients(), jsonOptions),
                    "update" => UpdatePatient(JsonSerializer.Deserialize<Patient>(root.GetProperty("data").GetRawText(), jsonOptions)!),
                    "delete" => DeletePatient(root.GetProperty("id").GetInt32()),
                    _ => Error("Невідома команда")
                };
            }
            catch (Exception ex)
            {
                return Error(ex.Message);
            }
        }

        private static string AddPatient(Patient p)
        {
            using var conn = new SQLiteConnection(connectionString);
            conn.Open();
            var cmd = conn.CreateCommand();
            cmd.CommandText = "INSERT INTO Patients (FullName, BirthDate, Diagnosis, Ward) VALUES (@f, @b, @d, @w)";
            cmd.Parameters.AddWithValue("@f", p.FullName);
            cmd.Parameters.AddWithValue("@b", p.BirthDate.ToString("yyyy-MM-dd"));
            cmd.Parameters.AddWithValue("@d", (object)p.Diagnosis ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@w", (object)p.Ward ?? DBNull.Value);
            cmd.ExecuteNonQuery();
            return Success();
        }

        private static List<Patient> GetPatients()
        {
            var list = new List<Patient>();
            using var conn = new SQLiteConnection(connectionString);
            conn.Open();
            using var cmd = conn.CreateCommand();
            cmd.CommandText = "SELECT Id, FullName, BirthDate, Diagnosis, Ward FROM Patients";
            using var r = cmd.ExecuteReader();
            while (r.Read())
            {
                list.Add(new Patient
                {
                    Id = r.GetInt32(0),
                    FullName = r.GetString(1),
                    BirthDate = DateTime.Parse(r.GetString(2)),
                    Diagnosis = r.IsDBNull(3) ? "" : r.GetString(3),
                    Ward = r.IsDBNull(4) ? "" : r.GetString(4)
                });
            }
            return list;
        }

        private static string UpdatePatient(Patient p)
        {
            using var conn = new SQLiteConnection(connectionString);
            conn.Open();
            var cmd = conn.CreateCommand();
            cmd.CommandText = "UPDATE Patients SET FullName=@f, BirthDate=@b, Diagnosis=@d, Ward=@w WHERE Id=@id";
            cmd.Parameters.AddWithValue("@id", p.Id);
            cmd.Parameters.AddWithValue("@f", p.FullName);
            cmd.Parameters.AddWithValue("@b", p.BirthDate.ToString("yyyy-MM-dd"));
            cmd.Parameters.AddWithValue("@d", (object)p.Diagnosis ?? DBNull.Value);
            cmd.Parameters.AddWithValue("@w", (object)p.Ward ?? DBNull.Value);
            cmd.ExecuteNonQuery();
            return Success();
        }

        private static string DeletePatient(int id)
        {
            using var conn = new SQLiteConnection(connectionString);
            conn.Open();
            var cmd = conn.CreateCommand();
            cmd.CommandText = "DELETE FROM Patients WHERE Id=@id";
            cmd.Parameters.AddWithValue("@id", id);
            cmd.ExecuteNonQuery();
            return Success();
        }

        private static string Success() => "{\"success\":true}";
        private static string Error(string msg) => $"{{\"success\":false,\"message\":\"{msg.Replace("\"", "\\\"")}\"}}";
    }
}