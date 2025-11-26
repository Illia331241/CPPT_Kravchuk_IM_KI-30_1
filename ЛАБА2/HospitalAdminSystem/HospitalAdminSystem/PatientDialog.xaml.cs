using HospitalAdminSystem.Models;
using System.Windows;

namespace HospitalAdminSystem
{
    public partial class PatientDialog : Window
    {
        public Patient Patient { get; }

        public PatientDialog(Patient? p = null)
        {
            InitializeComponent();

            Patient = p == null
                ? new Patient()
                : new Patient
                {
                    Id = p.Id,
                    FullName = p.FullName,
                    BirthDate = p.BirthDate,
                    Diagnosis = p.Diagnosis,
                    Ward = p.Ward
                };

            this.DataContext = Patient;
        }

        private void Save_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
        }

        private void Cancel_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = false;
        }
    }
}