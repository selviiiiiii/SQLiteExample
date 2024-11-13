package id.ac.polbeng.selvi.sqliteexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.ac.polbeng.selvi.sqliteexample.databinding.ActivityCreateBinding
class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var studentDBHelper: StudentDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        studentDBHelper = StudentDBHelper(this)
        binding.btnSimpan.setOnClickListener {
            val nim = binding.etNIM.text.toString()
            val name = binding.etNama.text.toString()
            val age = binding.etUmur.text.toString()
            if(nim.isEmpty() || name.isEmpty() || age.isEmpty()){
                Toast.makeText(this, "Silah masukan data nim, nama, dan umur!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }
            val newStudent = StudentModel(nim = nim, name = name, age
            = age)
            val result = studentDBHelper.createStudent(newStudent)
            if(result > -1){
                Toast.makeText(this, "Sukses menambahkan data dengan primary key: $result", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Gagal menambahkan data dengan nim $nim", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroy() {
        studentDBHelper.close()
        super.onDestroy()
    }
}
