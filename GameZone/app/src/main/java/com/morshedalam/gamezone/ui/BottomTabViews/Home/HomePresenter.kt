package com.morshedalam.gamezone.ui.BottomTabViews.Home

import android.util.Log
import com.morshedalam.gamezone.model.Game
import com.morshedalam.gamezone.repository.RemoteRepository
import com.morshedalam.gamezone.repository.UserSharePrefsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePresenter(val repository: RemoteRepository,val view:HomeContract.View):HomeContract.Presenter {

    private  val TAG = "GAMES"

    override fun start() {

       val userdata = UserSharePrefsRepository.getUserData()
        userdata?.let { userdata ->

            repository.retrieveAllGamesItems(userdata.userId, object :Callback<Game>{


                override fun onResponse(call: Call<Game>?, response: Response<Game>?) {
                    val games = response?.body()
                    if (games != null){
//                        Log.d(TAG,"${games.data}" )
                       view.showHomesData(games.data)
                    }else{
                        Log.e(TAG, "error games items retrieving")
                    }
                }

                override fun onFailure(call: Call<Game>?, t: Throwable?) {
                    Log.e(TAG, "error games items retrieving")
                }



            })


        }
    }

    override fun loadGameData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}