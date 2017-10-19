package com.samkeet.takeup.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.samkeet.takeup.R

/**
 * Created by knightshade on 10/19/17.
 */
class ImageFragment : Fragment() {
    private var mResourceId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mResourceId = arguments.getInt("resourceId")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragement_image_indicator, container, false)
        val imageView = view.findViewById(R.id.image_view) as ImageView
        imageView.setImageResource(mResourceId)
        return view
    }

    companion object {

        fun newInstance(resourceId: Int): ImageFragment {
            val imageFragment = ImageFragment()
            val args = Bundle()
            args.putInt("resourceId", resourceId)
            imageFragment.arguments = args
            return imageFragment
        }
    }
}
