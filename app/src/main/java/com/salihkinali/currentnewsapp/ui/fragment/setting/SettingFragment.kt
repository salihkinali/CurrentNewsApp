package com.salihkinali.currentnewsapp.ui.fragment.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View

import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.salihkinali.currentnewsapp.R


class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        findPreference<Preference>("developer")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openView(getString(R.string.developer_adress))
                true
            }
        findPreference<Preference>("source_code")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openView(getString(R.string.githup_adress))
                true
            }
        findPreference<Preference>("send_feedback")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openView(getString(R.string.feedback_adress))
                true
            }
        findPreference<Preference>("share")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                shareCode(getString(R.string.githup_adress))
                true
            }
    }

    private fun shareCode(share: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, share)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

    private fun openView(url: String) {
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(url)
        )
        startActivity(viewIntent)
    }
}