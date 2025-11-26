using HospitalAdminSystem.Models;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;

namespace HospitalAdminSystem.ViewModels
{
    public class MainViewModel : INotifyPropertyChanged
    {
        private readonly TcpService tcp = new();
        private ObservableCollection<Patient> allPatients = new();

        public ObservableCollection<Patient> Patients { get; } = new();

        private Patient? selected;
        public Patient? SelectedPatient
        {
            get => selected;
            set { selected = value; OnPropertyChanged(nameof(SelectedPatient)); }
        }

        public void Load()
        {
            allPatients = new ObservableCollection<Patient>(tcp.GetAll());
            Patients.Clear();
            foreach (var p in allPatients.OrderBy(p => p.FullName))
                Patients.Add(p);
        }

        public void ApplyFilters(string search = "", string diagnosis = "", string ward = "")
        {
            var query = allPatients.AsEnumerable();

            if (!string.IsNullOrWhiteSpace(search))
                query = query.Where(p => p.FullName.Contains(search, System.StringComparison.OrdinalIgnoreCase));

            if (!string.IsNullOrEmpty(diagnosis))
                query = query.Where(p => p.Diagnosis == diagnosis);

            if (!string.IsNullOrEmpty(ward))
                query = query.Where(p => p.Ward == ward);

            Patients.Clear();
            foreach (var p in query.OrderBy(p => p.FullName))
                Patients.Add(p);
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        private void OnPropertyChanged(string name) =>
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(name));
    }
}