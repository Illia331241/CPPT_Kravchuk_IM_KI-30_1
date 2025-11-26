namespace HospitalAdminSystem.Models
{
    public class Patient
    {
        public int Id { get; set; }
        public string FullName { get; set; } = "";
        public DateTime BirthDate { get; set; }
        public string Diagnosis { get; set; } = "";
        public string Ward { get; set; } = "";
    }
}