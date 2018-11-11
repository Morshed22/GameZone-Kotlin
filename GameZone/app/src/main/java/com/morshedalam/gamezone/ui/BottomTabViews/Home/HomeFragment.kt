package com.morshedalam.gamezone.ui.BottomTabViews.Home

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.request.RequestOptions
import com.glide.slider.library.Animations.DescriptionAnimation
import com.glide.slider.library.Indicators.PagerIndicator
import com.glide.slider.library.SliderLayout
import com.glide.slider.library.SliderTypes.BaseSliderView
import com.glide.slider.library.SliderTypes.DefaultSliderView
import com.glide.slider.library.SliderTypes.TextSliderView
import com.glide.slider.library.Tricks.ViewPagerEx

import com.morshedalam.gamezone.R
import com.morshedalam.gamezone.model.Game
import com.morshedalam.gamezone.repository.RemoteRepository
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeContract.View, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    override lateinit var presenter: HomeContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = HomePresenter(RemoteRepository(), this)
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun showHomesData(items: List<Game.Data>) {
        setImageSlideShow(items)
    }

    private fun setImageSlideShow(gameitem: List<Game.Data>) {
        val requestOption = RequestOptions()


        for (item in gameitem) {


            val sliderView = DefaultSliderView(context)
            sliderView
                    .image("http://game.gagagugu.com/gameimages/${item.slider}")
                    .setRequestOption(requestOption.centerCrop())
                    .setBackgroundColor(Color.WHITE)
                    .setProgressBarVisible(false)
                    //.setOnSliderClickListener(this)



            slider.addSlider(sliderView)
        }
        slider.indicatorVisibility = PagerIndicator.IndicatorVisibility.Invisible


//        slider.setPresetTransformer(SliderLayout.Transformer.Default)
//        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
//        slider.setCustomIndicator(custom_indicator)
//        slider.setCustomAnimation(DescriptionAnimation())
        //slider.setDuration(4000)
        //slider.addOnPageChangeListener(this)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStop() {
        slider.stopAutoCycle()
        super.onStop()
    }

    override fun onSliderClick(basesliserView: BaseSliderView?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

}
