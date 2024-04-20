package com.example.finalprojectwithanimation

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView


/**
 * Yes Fragment (Button Yes Pressed)
 */
class YesFragment : Fragment() {

    //Aimation stuff:
    private var videoview: VideoView? = null
    private var currentPosition = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val screenWidthDp = resources.configuration.screenHeightDp
        val layoutResId = when {
            screenWidthDp >= 650 -> R.layout.fragment_yes
            else -> R.layout.fragment_yes_small
        }
        val view_yes = inflater.inflate(layoutResId, container, false)

        //Load the Animation:
        videoview = view_yes.findViewById(R.id.videoView3)
        val looking_up_path = "android.resource://${requireContext().packageName}/${R.raw.looking_up_anim}"
        val imgView: ImageView = view_yes.findViewById(R.id.temp)

        //set the media Controller:
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoview!!)
        videoview!!.setVideoURI(Uri.parse(looking_up_path))
        videoview!!.requestFocus()
        videoview!!.start()
        //delay the appearance of the animation video:
        videoview!!.visibility = View.VISIBLE
        Handler().postDelayed({
            imgView.visibility = View.GONE

        }, 601)



        return view_yes
    }

    //Code for playing the animation if the screen is minimised:
    override fun onStart() {
        super.onStart()
        videoview?.seekTo(currentPosition)
        videoview?.start()
    }

    override fun onResume() {
        super.onResume()
        if (!videoview!!.isPlaying) {
            videoview?.seekTo(currentPosition)
            videoview?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        videoview?.pause()
        currentPosition = videoview?.currentPosition ?: 0
    }

    override fun onStop() {
        videoview?.pause()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("position", currentPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position")
            videoview?.seekTo(currentPosition)
        }
    }

}