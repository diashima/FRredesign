package kz.tengrilab.frredesign.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kz.tengrilab.frredesign.MainActivity
import kz.tengrilab.frredesign.Variables
import kz.tengrilab.frredesign.api.ApiClient
import kz.tengrilab.frredesign.api.AuthInterface
import kz.tengrilab.frredesign.data.Auth
import kz.tengrilab.frredesign.databinding.ActivityLoginBinding
import kz.tengrilab.frredesign.showSnack
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callback : OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    finish()
                    exitProcess(0)
                }
            }
        onBackPressedDispatcher.addCallback(this, callback)

        if (isSavedLogin()) {
            binding.inputUserName.setText(getLogin())
            binding.inputPassword.setText(getPassword())
            binding.checkbox2.isChecked = true
        }

        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked)
                binding.inputPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            else
                binding.inputPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }

        binding.btnAuth.setOnClickListener {
            val uName = binding.inputUserName.text.toString()
            val uPass = binding.inputPassword.text.toString()
            if (uName == "" || uPass == "")
                Toast.makeText(applicationContext,"Заполните поля", Toast.LENGTH_SHORT).show()
            else {
                if (binding.checkbox2.isChecked) {
                    saveLogin(true)
                } else {
                    saveLogin(false)
                }
                signIn(uName, uPass)
            }
        }
    }

    private fun signIn(username: String, password: String) {
        val retrofit = ApiClient.getRetrofitClient()
        val authInterface = retrofit.create(AuthInterface::class.java)

        val deviceId = Variables.getDeviceId(applicationContext)
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .addFormDataPart("code", deviceId)
            .build()
        val call = authInterface.getUser(body)
        call.enqueue(object: Callback<Auth> {
            override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                Log.d("Test", response.toString())
                if (response.code() == 200) {
                    if (response.body() != null) {
                        val token = response.body()!!.token
                        val userId = response.body()!!.userId
                        val name = response.body()!!.lastName + " " + response.body()!!.firstName + " " + response.body()!!.middleName
                        saveCredentials(username, token, userId, name, password)
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                else if (response.code() == 404) {
                    binding.textViewUser.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Auth>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                Log.d("Test", t.message!!)
            }

        })
    }

    private fun saveCredentials(login: String, token: String, userId: Int, username: String, password: String) {
        val sharedPref = getSharedPreferences(Variables.sharedPrefLogin, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(Variables.sharedPrefLogin, login)
            putString(Variables.sharedPrefToken, token)
            putString(Variables.username, username)
            putString(Variables.password, password)
            putInt(Variables.sharedPrefId, userId)
            apply()
        }
    }

    private fun saveLogin(boolean: Boolean) {
        val sharedPref = getSharedPreferences(Variables.sharedPrefLogin, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(Variables.saveLogin, boolean)
            apply()
        }
    }

    private fun isSavedLogin() : Boolean {
        val sharedPreferences = getSharedPreferences(Variables.sharedPrefLogin, Context.MODE_PRIVATE)!!
        return sharedPreferences.getBoolean(Variables.saveLogin, false)
    }

    private fun getLogin() : String? {
        val sharedPreferences = getSharedPreferences(Variables.sharedPrefLogin, Context.MODE_PRIVATE)!!
        return sharedPreferences.getString(Variables.sharedPrefLogin, "")
    }

    private fun getPassword() : String? {
        val sharedPreferences = getSharedPreferences(Variables.sharedPrefLogin, Context.MODE_PRIVATE)!!
        return sharedPreferences.getString(Variables.password, "")
    }
}