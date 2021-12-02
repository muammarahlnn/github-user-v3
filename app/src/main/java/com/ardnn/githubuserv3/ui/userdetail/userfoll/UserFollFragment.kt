package com.ardnn.githubuserv3.ui.userdetail.userfoll

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.githubuserv3.R
import com.ardnn.githubuserv3.api.responses.UserResponse
import com.ardnn.githubuserv3.databinding.FragmentUserFollBinding
import com.ardnn.githubuserv3.helper.ClickListener
import com.ardnn.githubuserv3.helper.Helper
import com.ardnn.githubuserv3.ui.userdetail.UserDetailActivity


class UserFollFragment : Fragment(), ClickListener<UserResponse> {
    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        private const val ARG_USERNAME = "username"

        fun newInstance(index: Int, username: String) =
            UserFollFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                    putString(ARG_USERNAME, username)
                }
            }
    }

    private lateinit var viewModel: UserFollViewModel
    private var _binding: FragmentUserFollBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserFollBinding.inflate(inflater, container, false)

        // set recyclerview
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserFoll.layoutManager = layoutManager

        // get args and set it as parameter on view model
        val section: Int = arguments?.getInt(ARG_SECTION_NUMBER, 0) ?: 0
        val username: String = arguments?.getString(ARG_USERNAME, "") ?: ""
        viewModel = ViewModelProvider(this, UserFollViewModelFactory(section, username))
            .get(UserFollViewModel::class.java)

        // subscribe view model
        subscribe(section)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        // make view pager's height flexible
        binding.root.requestLayout()
    }

    private fun subscribe(section: Int) {
        // fetch user foll depends on its section number
        fetchUserFoll(section)

        // show progressbar
        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            Helper.showLoading(binding.progressBar, isLoading)
        })

        // show alert if list is empty
        viewModel.isListEmpty.observe(viewLifecycleOwner, { isListEmpty ->
            showAlert(isListEmpty, resources.getString(R.string.no_users_found))
        })

        // show alert if unable to retrieve data
        viewModel.isFailure.observe(viewLifecycleOwner, { isFailure ->
            showAlert(isFailure, resources.getString(R.string.unable_to_retrieve_data))
        })
    }

    private fun fetchUserFoll(section: Int) {
        when (section) {
            0 -> { // user followers
                viewModel.followerList.observe(viewLifecycleOwner, { followerList ->
                    setUserFoll(followerList)
                })
            }
            1 -> { // user following
                viewModel.followingList.observe(viewLifecycleOwner, { followingList ->
                    setUserFoll(followingList)
                })
            }
        }
    }

    private fun setUserFoll(userFollList: List<UserResponse>) {
        val adapter = UserFollAdapter(userFollList, this)
        binding.rvUserFoll.adapter = adapter
    }

    private fun showAlert(isShow: Boolean, text: String) {
        with (binding) {
            if (isShow) {
                tvAlert.text = text
                tvAlert.visibility = View.VISIBLE
            } else {
                tvAlert.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClicked(t: UserResponse) {
        val toUserDetail = Intent(requireActivity(), UserDetailActivity::class.java)
        toUserDetail.putExtra(UserDetailActivity.EXTRA_USERNAME, t.username)
        startActivity(toUserDetail)
    }

}