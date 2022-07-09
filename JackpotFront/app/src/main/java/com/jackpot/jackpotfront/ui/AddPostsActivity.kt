package com.jackpot.jackpotfront.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ActivityAddPostsBinding
import com.jackpot.jackpotfront.retrofit.RetrofitService
import com.jackpot.jackpotfront.retrofit.data.PostPostResult
import com.jackpot.jackpotfront.retrofit.data.UserIdxObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class AddPostsActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddPostsBinding.inflate(layoutInflater) }
    val retro = RetrofitService.create()

    private val albumLauncher = albumResultLauncher()
    lateinit var myBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgBtn.setOnClickListener{
            val photointent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photointent.type = "image/*"
            albumLauncher.launch(photointent)
        }
        binding.saveBtn.setOnClickListener {

            // 파일 생성 시 주소는 앱 내장 주소에 이름은 img.jpg
            val myFile = File(filesDir, "img.jpg")
            val os : OutputStream
            // 파일 생성
            myFile.createNewFile()
            os = FileOutputStream(myFile)

            // 여기서 만든 myFile에 이미지 비트맵 집어넣음
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)

            os.close()

            // make requestBody
            var requestBody = RequestBody.create(MediaType.parse("image/*"), myFile)
            Log.d("MYTAG","requsetBody : "+requestBody.toString())
            // make Multipart.Part
            var file = MultipartBody.Part.createFormData("img", myFile.name ,requestBody)
            Log.d("MYTAG","file : "+file)

            // 본문 requestBody로
            var requestBody2 = RequestBody.create(MediaType.parse("text/plain"), binding.editText.text.toString())
            Log.d("MYTAG","본문 text : "+binding.editText.text.toString())
            Log.d("MYTAG","useridx text : "+UserIdxObject.userIdx.toString())


            // 레트로핏 통신
            retro.postPost(UserIdxObject.userIdx, file, requestBody2).enqueue(object : Callback<PostPostResult> {
                override fun onResponse(call: Call<PostPostResult>, response: Response<PostPostResult>) {
                    Log.d("MYTAG",response.body().toString())
                }

                override fun onFailure(call: Call<PostPostResult>, t: Throwable) {
                    Log.d("MYTAG",t.message.toString())
                    Log.d("MYTAG","FAIL")
                }
            })

            startActivity(Intent(this@AddPostsActivity,MainActivity::class.java))
        }
    }
    private fun albumResultLauncher() =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    try{
                        val calRatio = calculateInSampleSize(result.data!!.data!!,
                            resources.getDimensionPixelSize(R.dimen.imgSize),
                            resources.getDimensionPixelSize(R.dimen.imgSize))
                        val option = BitmapFactory.Options()
                        option.inSampleSize = calRatio

                        var inputStream = contentResolver.openInputStream(result.data!!.data!!)
                        Log.d("filePath","${result.data!!.data!!}")
                        val bitmap = BitmapFactory.decodeStream(inputStream,null,option)
                        inputStream!!.close()
                        inputStream = null

                        myBitmap = bitmap!!

                        binding.imgBtn.setImageBitmap(bitmap)
                        // 글라이드는 이미지 새로 고침 안됨
//                        Glide.with(this@AddPostsActivity)
//                            .load(myFile)
//                            .into(binding.imgBtn)

                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
                else -> {

                }
            }
        }
    private fun calculateInSampleSize(fileUri: Uri, reqWidth:Int, reqHeight:Int):Int{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try{
            var inputStream  = contentResolver.openInputStream(fileUri)
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        }catch (e: Exception){
            e.printStackTrace()
        }
        val (height: Int, width: Int) = options.run{outHeight to outWidth}
        var inSampleSize = 1
        if (height > reqHeight || width> reqWidth){
            val halfHeight : Int  = height / 2
            val halfWidth: Int = width / 2
            while(halfHeight/ inSampleSize >= reqHeight&&
                halfWidth / inSampleSize >= reqWidth){
                inSampleSize *=2
            }
        }
        return inSampleSize
    }
}