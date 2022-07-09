package com.jackpot.jackpotfront.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.jackpot.jackpotfront.R
import com.jackpot.jackpotfront.databinding.ActivityAddPostsBinding
import com.jackpot.jackpotfront.databinding.ActivityMainBinding
import java.io.File

class AddPostsActivity : AppCompatActivity() {
    val binding by lazy { ActivityAddPostsBinding.inflate(layoutInflater) }
    private val albumLauncher = albumResultLauncher()
    lateinit var filePath: File
    lateinit var filename: kotlin.String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imgBtn.setOnClickListener{
            val photointent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photointent.type = "image/*"
            albumLauncher.launch(photointent)
        }
        binding.saveBtn.setOnClickListener {
            //Retrofit 통신 필요
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
                        bitmap?.let {
                            binding.imgBtn.setImageBitmap(bitmap)
                        } ?: let{
                            Log.d("김 ","bitmap null")
                        }
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