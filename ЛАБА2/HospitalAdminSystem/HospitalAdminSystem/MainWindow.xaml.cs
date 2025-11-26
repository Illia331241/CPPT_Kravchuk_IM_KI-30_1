using HospitalAdminSystem.Models;
using HospitalAdminSystem.ViewModels;
using System.Linq;
using System.Windows;
using System.Windows.Controls;

namespace HospitalAdminSystem
{
    public partial class MainWindow : Window
    {
        private readonly MainViewModel vm = new();
        private readonly TcpService tcp = new();

        public MainWindow()
        {
            InitializeComponent();
            DataContext = vm;
            LoadFilters();
            vm.Load();
        }

        private void LoadFilters()
        {
            var all = tcp.GetAll();
            var diagnoses = all.Select(p => p.Diagnosis).Distinct().OrderBy(d => d).ToList();
            var wards = all.Select(p => p.Ward).Distinct().OrderBy(w => w).ToList();

            cbDiagnosis.ItemsSource = diagnoses;
            cbWard.ItemsSource = wards;
        }

        private void txtSearch_TextChanged(object sender, TextChangedEventArgs e) => ApplyFilters();
        private void cbDiagnosis_SelectionChanged(object sender, SelectionChangedEventArgs e) => ApplyFilters();
        private void cbWard_SelectionChanged(object sender, SelectionChangedEventArgs e) => ApplyFilters();

        private void btnClear_Click(object sender, RoutedEventArgs e)
        {
            txtSearch.Text = "";
            cbDiagnosis.SelectedIndex = -1;
            cbWard.SelectedIndex = -1;
            vm.Load();
        }

        private void btnRefresh_Click(object sender, RoutedEventArgs e)
        {
            LoadFilters();
            vm.Load();
        }

        private void ApplyFilters()
        {
            string search = txtSearch.Text.Trim();
            string diag = cbDiagnosis.SelectedItem as string ?? "";
            string ward = cbWard.SelectedItem as string ?? "";

            vm.ApplyFilters(search, diag, ward);
        }

        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            var dlg = new PatientDialog();
            if (dlg.ShowDialog() == true)
            {
                tcp.Add(dlg.Patient);
                vm.Load();
                LoadFilters();
            }
        }

        private void btnEdit_Click(object sender, RoutedEventArgs e)
        {
            if (vm.SelectedPatient == null) return;
            var dlg = new PatientDialog(vm.SelectedPatient);
            if (dlg.ShowDialog() == true)
            {
                tcp.Update(dlg.Patient);
                vm.Load();
            }
        }

        private void btnDelete_Click(object sender, RoutedEventArgs e)
        {
            if (vm.SelectedPatient == null) return;
            if (MessageBox.Show("Видалити пацієнта?", "Підтвердження", MessageBoxButton.YesNo) == MessageBoxResult.Yes)
            {
                tcp.Delete(vm.SelectedPatient.Id);
                vm.Load();
            }
        }
    }
}