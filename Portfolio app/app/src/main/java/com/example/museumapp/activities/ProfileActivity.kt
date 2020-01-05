package com.example.museumapp.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.museumapp.IMAGE_PICK_CODE
import com.example.museumapp.PERMISSION_CODE
import com.example.museumapp.R
import com.example.museumapp.Services.UserService
import kotlinx.android.synthetic.main.activity_profile.*
import model.User

class ProfileActivity : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.profileToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user = UserService.attemptGetUser(this)!!

        ivProfileImage.setOnClickListener { selectProfileImage() }
        llCommentsContainer.setOnClickListener { showComments() }
        llFavoritesContainer.setOnClickListener { showFavorites() }

        initViews()
    }

    private fun initViews() {
        if (user.profileImageUri != null) {
            println("Found profile image in db: ${user.profileImageUri}")
            ivProfileImage.setImageURI(Uri.parse(user.profileImageUri))
        }
        tvUsername.text = user.username
        tvEmail.text = user.email
    }

    private fun selectProfileImage() {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                //permission already granted
                pickImageFromGallery()
            }
        } else{
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            user.profileImageUri = data?.data.toString()
            println("Got data from image select: ${data?.data}")
            UserService.updateUser(this, user)
            ivProfileImage.setImageURI(Uri.parse(user.profileImageUri))
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showComments() {
        val intent = Intent(this, UserCommentsActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun showFavorites() {
        val intent = Intent(this, UserFavoritesActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}
