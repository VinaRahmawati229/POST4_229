package com.vina.post4_229;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etNama, etNik, etKabupaten, etKecamatan, etDesa, etRt, etRw;
    private RadioGroup rgJenisKelamin;
    private Spinner spinnerStatusPernikahan;
    private Button btnSimpan, btnReset;
    private LinearLayout containerDataWarga;
    private TextView tvEmptyData;

    private WargaRepository wargaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Repository
        wargaRepository = new WargaRepository(getApplication());

        initViews();
        setupSpinner();
        setupButtons();
        loadDataWarga();
    }

    private void initViews() {
        etNama = findViewById(R.id.etNama);
        etNik = findViewById(R.id.etNik);
        etKabupaten = findViewById(R.id.etKabupaten);
        etKecamatan = findViewById(R.id.etKecamatan);
        etDesa = findViewById(R.id.etDesa);
        etRt = findViewById(R.id.etRt);
        etRw = findViewById(R.id.etRw);
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin);
        spinnerStatusPernikahan = findViewById(R.id.spinnerStatusPernikahan);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnReset = findViewById(R.id.btnReset);
        containerDataWarga = findViewById(R.id.containerDataWarga);
        tvEmptyData = findViewById(R.id.tvEmptyData);
    }

    private void setupSpinner() {
        String[] statusPernikahan = {"Belum Menikah", "Menikah", "Cerai"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusPernikahan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusPernikahan.setAdapter(adapter);
    }

    private void setupButtons() {
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();
            }
        });
    }

    private void simpanData() {
        // Get input values
        String nama = etNama.getText().toString().trim();
        String nik = etNik.getText().toString().trim();
        String kabupaten = etKabupaten.getText().toString().trim();
        String kecamatan = etKecamatan.getText().toString().trim();
        String desa = etDesa.getText().toString().trim();
        String rt = etRt.getText().toString().trim();
        String rw = etRw.getText().toString().trim();

        // Get selected radio button
        String jenisKelamin = "";
        int selectedGenderId = rgJenisKelamin.getCheckedRadioButtonId();
        if (selectedGenderId == R.id.rbLaki) {
            jenisKelamin = "Laki-Laki";
        } else if (selectedGenderId == R.id.rbPerempuan) {
            jenisKelamin = "Perempuan";
        }

        // Get selected status pernikahan
        String statusPernikahan = spinnerStatusPernikahan.getSelectedItem().toString();

        // Validation
        if (nama.isEmpty() || nik.isEmpty() || kabupaten.isEmpty() || kecamatan.isEmpty() ||
                desa.isEmpty() || rt.isEmpty() || rw.isEmpty() || jenisKelamin.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nik.length() != 16) {
            Toast.makeText(this, "NIK harus 16 digit!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Warga object
        Warga warga = new Warga(nama, nik, kabupaten, kecamatan, desa, rt, rw, jenisKelamin, statusPernikahan);

        // Save to database
        long id = wargaRepository.insert(warga);

        if (id != -1) {
            Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
            clearForm();
            loadDataWarga();
        } else {
            Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetData() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Reset Data")
                .setMessage("Apakah Anda yakin ingin menghapus semua data?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    wargaRepository.deleteAll();
                    clearForm();
                    loadDataWarga();
                    Toast.makeText(this, "Semua data telah direset!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    private void clearForm() {
        etNama.setText("");
        etNik.setText("");
        etKabupaten.setText("");
        etKecamatan.setText("");
        etDesa.setText("");
        etRt.setText("");
        etRw.setText("");
        rgJenisKelamin.clearCheck();
        spinnerStatusPernikahan.setSelection(0);
    }

    private void loadDataWarga() {
        containerDataWarga.removeAllViews();

        List<Warga> wargaList = wargaRepository.getAllWarga();

        if (wargaList.isEmpty()) {
            tvEmptyData.setVisibility(View.VISIBLE);
            containerDataWarga.setVisibility(View.GONE);
        } else {
            tvEmptyData.setVisibility(View.GONE);
            containerDataWarga.setVisibility(View.VISIBLE);

            for (int i = 0; i < wargaList.size(); i++) {
                Warga warga = wargaList.get(i);
                addDataItem(warga, i + 1);
            }
        }
    }

    private void addDataItem(Warga warga, int number) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setBackgroundResource(R.drawable.data_item_background);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16);
        itemLayout.setLayoutParams(layoutParams);
        itemLayout.setPadding(16, 16, 16, 16);

        TextView tvNumberName = new TextView(this);
        tvNumberName.setText(number + ". " + warga.getNama() + " (" + warga.getJenisKelamin() + ") - " + warga.getStatusPernikahan());
        tvNumberName.setTextSize(14);
        tvNumberName.setTextColor(getResources().getColor(android.R.color.black));
        tvNumberName.setTypeface(tvNumberName.getTypeface(), android.graphics.Typeface.BOLD);

        TextView tvNik = new TextView(this);
        tvNik.setText("NIK: " + warga.getNik());
        tvNik.setTextSize(12);
        tvNik.setTextColor(getResources().getColor(android.R.color.black));
        tvNik.setPadding(0, 4, 0, 0);

        TextView tvAlamat = new TextView(this);
        tvAlamat.setText("Alamat: RT " + warga.getRt() + "/RW " + warga.getRw() + ", " +
                warga.getDesa() + ", " + warga.getKecamatan() + ", " + warga.getKabupaten());
        tvAlamat.setTextSize(12);
        tvAlamat.setTextColor(getResources().getColor(android.R.color.black));
        tvAlamat.setPadding(0, 4, 0, 0);

        itemLayout.addView(tvNumberName);
        itemLayout.addView(tvNik);
        itemLayout.addView(tvAlamat);

        containerDataWarga.addView(itemLayout);
    }
}