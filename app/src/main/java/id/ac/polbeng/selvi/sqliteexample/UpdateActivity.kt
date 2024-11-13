package id.ac.polbeng.selvi.sqliteexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.ac.polbeng.selvi.sqliteexample.databinding.ActivityUpdateBinding
class UpdateActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_STUDENT = "extra_student"
    }
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var studentDBHelper: StudentDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val studentData =
            intent.getParcelableExtra<StudentModel>(EXTRA_STUDENT) as StudentModel
        binding.etNIM.isEnabled = false
        binding.etNIM.setText(studentData.nim)
        binding.etNama.setText(studentData.name)
        binding.etUmur.setText(studentData.age)
        studentDBHelper = StudentDBHelper(this)
        binding.btnUpdate.setOnClickListener {
            val nim = binding.etNIM.text.toString()
            val name = binding.etNama.text.toString()
            val age = binding.etUmur.text.toString()
            if(nim.isEmpty() || name.isEmpty() || age.isEmpty()){
                Toast.makeText(this, "Silah masukan data nim, nama, dan umur!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }
            val newUpdateStudent = StudentModel(nim = nim, name =
            name, age = age)
            val updateCount =
                studentDBHelper.updateStudent(newUpdateStudent)
            if(updateCount > 0){
                Toast.makeText(this, "Mahasiswa yang terupdate :$updateCount", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Tidak ada data mahasiswa yang diupdate silahkan coba lagi!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnHapus.setOnClickListener {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Konfirmasi Hapus Data")
                .setMessage("Apakah anda yakin?")
                .setPositiveButton("Ya") { _, _ ->
                    val nim = binding.etNIM.text.toString()
                    val deleteCount =
                        studentDBHelper.deleteStudent(nim)
                    if(deleteCount > 0) {
                        Toast.makeText(this, "Mahasiswa yang terhapus: $deleteCount", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Tidak ada data mahasiswa yang dihapus silahkan coba lagi!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Tidak") { _, _ ->
                    // User cancelled the dialog.
                }
            // Create the AlertDialog object and return it.
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    override fun onDestroy() {
        studentDBHelper.close()
        super.onDestroy()
    }
}