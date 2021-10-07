package com.vinted.demovinted.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vinted.demovinted.base.BaseFragment
import com.vinted.demovinted.R
import com.vinted.demovinted.databinding.FragmentItemDetailsPageBinding

class ItemDetailsPageFragment : BaseFragment() {

    private val args: ItemDetailsPageFragmentArgs by navArgs()
    private lateinit var binding: FragmentItemDetailsPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupViews(rootView: View) = with(binding) {
        ivIconBack.setOnClickListener {
            findNavController().popBackStack()
        }

        args.item.let { item ->
            Glide.with(ivLogo)
                .load(item.fullPhotoURL)
                .centerCrop()
                .placeholder(R.drawable.bg_image_place_holder)
                .into(ivLogo)

            tvToolbar.text = item.brand
            tvPrice.text = String.format(resources.getString(R.string.price), item.priceFormatted)
            tvBrand.text = String.format(resources.getString(R.string.brand), item.brand)
            item.size?.let {
                tvSize.text = String.format(resources.getString(R.string.size), it)
            } ?: run {
                tvSize.isGone = true
            }
        }
    }
}
