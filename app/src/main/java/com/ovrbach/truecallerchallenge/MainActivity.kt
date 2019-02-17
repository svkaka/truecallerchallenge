package com.ovrbach.truecallerchallenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ovrbach.truecallerchallenge.common.RequestStatus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setupLiveDataObservation()

        main_button_submit.setOnClickListener {
            makeRequest()
        }

    }

    private fun setupLiveDataObservation() {
        viewModel.resultA.observe(this, Observer { response ->
            when (response) {
                is RequestStatus.Success -> {
                    onSuccess()
                    main_a.text = getString(R.string.string_a, response.data.toString())
                }
                is RequestStatus.Failure -> {
                    onError(response.error.localizedMessage)
                }
                is RequestStatus.Requesting -> {
                    onStartRequesting()
                }
                is RequestStatus.Cancelled -> {
                    //TODO
                }
            }
        })

        viewModel.resultB.observe(this, Observer { response ->
            when (response) {
                is RequestStatus.Success -> {
                    onSuccess()
                    main_b.text = response.data.toString()
                }
                is RequestStatus.Failure -> {
                    onError(response.error.localizedMessage)
                }
                is RequestStatus.Requesting -> {
                    onStartRequesting()
                }
                is RequestStatus.Cancelled -> {
                    //TODO
                }
            }
        })

        viewModel.resultC.observe(this, Observer { response ->
            when (response) {
                is RequestStatus.Success -> {
                    onSuccess()
                    main_c.text = response.data.toString()
                }
                is RequestStatus.Failure -> {
                    onError(response.error.localizedMessage)
                }
                is RequestStatus.Requesting -> {
                    onStartRequesting()
                }
                is RequestStatus.Cancelled -> {
                    //TODO
                }
            }
        })
    }

    private fun onStartRequesting() {
        main_progress.show()
        main_exception_text.hide()
        main_results_layout.hide()
        main_button_submit.hide()
    }

    private fun onError(error: String) {
        main_exception_text.text = getString(R.string.exception, error)

        main_progress.hide()
        main_exception_text.show()
        main_results_layout.hide()

        main_button_submit.show()
        main_button_submit.text = getString(R.string.try_again)
    }

    private fun onSuccess() {
        main_progress.hide()
        main_exception_text.hide()
        main_results_layout.show()

        main_button_submit.show()
        main_button_submit.text = getString(R.string.submit)
    }

    private fun makeRequest() {
        viewModel.runRequests()
    }

}
