package com.morshedalam.gamezone

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {



    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult)
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }


    companion object {
        private const val FB_TAG = "FB_TAG"
        private const val GMAIL_TAG = "GMAIL_TAG"

            private val TAG = "SignInActivity"
            private val RC_SIGN_IN = 9001

    }

    val GM_SIGN_IN: Int = 1
    val FB_SIGN_IN: Int = 2

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener:FirebaseAuth.AuthStateListener

    //facebook
    private var callbackManager: CallbackManager? = null


   //google
    private lateinit var gso : GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        authListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.getCurrentUser()
                if (user != null)
                {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid())
                }
                else
                {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out")
                }
            }

        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (callbackManager != null){
            callbackManager!!.onActivityResult(requestCode, resultCode,data)
        }

//        if (requestCode == GM_SIGN_IN) {
//            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleResult (task)
//        }else {
//            Toast.makeText(this, "Problem in execution order :(", Toast.LENGTH_LONG).show()
//        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }





    fun onClickSignInButton(view: View){
//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, GM_SIGN_IN)

        authenticateWithFaceBook()
    }


    private fun handleResult (completedTask: Task<GoogleSignInAccount>) {
//        try {
//            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!
//            updateUI (account)
//        } catch (e: ApiException) {
//            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
//        }
    }

    private fun updateUI (account: GoogleSignInAccount) {
        text_id.visibility = View.VISIBLE
        text_id.text = account.displayName
    }


    // faceBook authentication

    private fun authenticateWithFaceBook(){
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_birthday", "user_friends"))
        //LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("email", "public_profile", "user_birthday", "user_friends"))
        LoginManager.getInstance().registerCallback(callbackManager,object : FacebookCallback<LoginResult>{

            override fun onSuccess(loginResult: LoginResult) {
                Log.d(FB_TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(FB_TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(FB_TAG, "facebook:onError", error)
                // ...
            }

        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(FB_TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(FB_TAG, "signInWithCredential:success")
                        val user = auth.currentUser
                        updateFBUserInfo(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(FB_TAG, "signInWithCredential:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateFBUserInfo(null)
                    }

                    // ...
                }
    }

    private fun updateFBUserInfo(user:FirebaseUser?){
         user?.let { user  ->
             text_id.visibility = View.VISIBLE
             text_id.text = user.displayName
         }
    }

}
