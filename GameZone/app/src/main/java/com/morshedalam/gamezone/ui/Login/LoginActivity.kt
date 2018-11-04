package com.morshedalam.gamezone.ui.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.*
import com.morshedalam.gamezone.R
import com.morshedalam.gamezone.model.User
import com.morshedalam.gamezone.repository.RemoteRepository
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity(),LoginContract.View, GoogleApiClient.OnConnectionFailedListener {


    lateinit var presenter: LoginContract.Presenter

    companion object {

        private val TAG = "LoginActivity"
        private val RC_SIGN_IN = 9001

    }


    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    //facebook
    private var callbackManager: CallbackManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setAutheListener()
        setPresenter()
    }

    private fun setPresenter(){
      presenter = LoginPresenter(RemoteRepository(), this)
    }


    private fun setAutheListener(){
        auth = FirebaseAuth.getInstance()
        authListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.getCurrentUser()
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid())
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out")
                }
            }

        }
    }

    fun onClickSignInButton(view: View) {

        when (view.id) {
            R.id.gmailSignIn -> authenticateWithGoogle()
            R.id.fbSignIn -> authenticateWithFaceBook()
        }

    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }

    override fun showUser(user: User) {
        text_id.visibility = View.VISIBLE
        text_id.text = user.userData.userName
        Log.d(TAG, user.msg)
    }

    override fun showErrorRetrievingUser() {
        Toast.makeText(baseContext, "User Data found null",
                Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (callbackManager != null) {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess()) {
                val account = result.getSignInAccount()
                account?.let { account ->
                    firebaseAuthWithGoogle(account)
                }

            } else {
                Log.e(TAG, "Google Sign In failed.")
            }
        }

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }




    //google authentication
    private fun authenticateWithGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
       val googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()


        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId())
        val credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        sendDataUserDataThroughAPi(SocialType.GMAIL, user)
                    } else {
                        Toast.makeText(baseContext, " Google Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                }


    }


    // faceBook authentication

    private fun authenticateWithFaceBook() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_birthday", "user_friends"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                firebaseAuthWithFaceBook(loginResult.accessToken)

            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")

            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)

            }

        })
    }

    private fun firebaseAuthWithFaceBook(token: AccessToken) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        sendDataUserDataThroughAPi(SocialType.FACEBOOK, user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "FaceBook Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }


                }
    }



    private fun sendDataUserDataThroughAPi(socialType: SocialType, user: FirebaseUser?) {

        presenter.retrieveUser(socialType, user)

   }

}
