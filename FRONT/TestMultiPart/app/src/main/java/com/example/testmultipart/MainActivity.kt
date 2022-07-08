package com.example.testmultipart

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.example.testmultipart.databinding.ActivityMainBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val retro = RetrofitService.create()

    var imgName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            galleryLauncherTwo()
        }

        // 이거 통신으로 받아와야 요구사항에 맞음
        binding.button2.setOnClickListener {
            Glide.with(this@MainActivity)
                .load("https://hana-umc.shop/test/display/" + imgName)
                .into(binding.imageView)
        }
    }


    fun galleryLauncherTwo() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 9999)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== 9999 && resultCode==RESULT_OK && data!=null) {
            Log.d("MYTAG", "test success")

            val myUri = data.data
            Log.d("MYTAG", myUri.toString())
            val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),myUri)


            // 파일 생성 시 주소는 앱 내장 주소에 이름은 img.jpg
            val myFile = File(filesDir, "img.jpg")
            val os : OutputStream
            // 파일 생성
            myFile.createNewFile()
            os = FileOutputStream(myFile)

            // 여기서 만든 myFile에 이미지 비트맵 집어넣음
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os)

            os.close()

//            Glide.with(this@MainActivity)
//                .load(myFile)
//                .into(binding.imageView)

//            var myFile = File(myUri.toString())

            Log.d("MYTAG","myFile : "+myFile)

            // make requestBody
            var requestBody = RequestBody.create(MediaType.parse("image/*"), myFile)
            Log.d("MYTAG","requsetBody : "+requestBody.toString())
            // make Multipart.Part
            var file = MultipartBody.Part.createFormData("file", myFile.name ,requestBody)
            Log.d("MYTAG","file : "+file)
            Log.d("MYTAG","myFile.name : "+myFile.name)
            Log.d("MYTAG","myFile.path : "+myFile.path)




            // 옷 업로드
            retro.uploadIMG(file).enqueue(object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("MYTAG","옷 업로드 : "+response.body())
                    Log.d("MYTAG","SUCCESS")
                    Log.d("MYTAG",myFile.path)

                    imgName = response.body()!!.substring(3)
                    Log.d("MYTAG", "imgName:"+imgName)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("MYTAG",t.message.toString())
                    Log.d("MYTAG","FAIL")
                }
            })
        }
    }
}