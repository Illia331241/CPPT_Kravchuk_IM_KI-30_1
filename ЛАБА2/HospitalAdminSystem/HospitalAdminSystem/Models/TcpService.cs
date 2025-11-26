using HospitalAdminSystem.Models;
using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;

namespace HospitalAdminSystem.Models
{
    public class TcpService
    {
        private const string IP = "127.0.0.1";
        private const int PORT = 12345;
        private static readonly JsonSerializerOptions opt = new() { PropertyNameCaseInsensitive = true };

        public List<Patient> GetAll()
        {
            try
            {
                using TcpClient client = new TcpClient();
                client.Connect(IP, PORT);
                NetworkStream stream = client.GetStream();

                // Відправляємо запит з довжиною
                string request = "{\"command\":\"get\"}";
                byte[] data = Encoding.UTF8.GetBytes(request);
                byte[] len = BitConverter.GetBytes(data.Length);

                stream.Write(len, 0, 4);
                stream.Write(data, 0, data.Length);

                // Читаємо довжину відповіді
                byte[] lenBuffer = new byte[4];
                int read = 0;
                while (read < 4)
                {
                    int r = stream.Read(lenBuffer, read, 4 - read);
                    if (r == 0) throw new Exception("Сервер закрив з'єднання");
                    read += r;
                }
                int responseLength = BitConverter.ToInt32(lenBuffer, 0);

                // Читаємо всю відповідь
                byte[] responseBuffer = new byte[responseLength];
                read = 0;
                while (read < responseLength)
                {
                    int r = stream.Read(responseBuffer, read, responseLength - read);
                    if (r == 0) throw new Exception("Сервер закрив з'єднання під час читання");
                    read += r;
                }

                string json = Encoding.UTF8.GetString(responseBuffer);
                return JsonSerializer.Deserialize<List<Patient>>(json, opt)!;
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("TCP помилка: " + ex.Message);
                return new List<Patient>();
            }
        }

        public void Add(Patient p) => SendCommand("add", p);
        public void Update(Patient p) => SendCommand("update", p);
        public void Delete(int id) => SendCommand("delete", new { id });

        private void SendCommand(string cmd, object obj)
        {
            try
            {
                using TcpClient client = new TcpClient();
                client.Connect(IP, PORT);
                NetworkStream stream = client.GetStream();

                string json = cmd == "delete"
                    ? $"{{\"command\":\"delete\",\"id\":{((dynamic)obj).id}"
                    : $"{{\"command\":\"{cmd}\",\"data\":{JsonSerializer.Serialize(obj)}}}";

                byte[] data = Encoding.UTF8.GetBytes(json);
                byte[] len = BitConverter.GetBytes(data.Length);

                stream.Write(len, 0, 4);
                stream.Write(data, 0, data.Length);
                // НЕ ЧЕКАЄМО ВІДПОВІДЬ — сервер просто приймає і все
            }
            catch { }
        }
    }
}