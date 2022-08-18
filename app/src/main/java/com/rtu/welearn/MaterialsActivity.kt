package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtu.welearn.databinding.ActivityMaterialsBinding
import com.rtu.welearn.utils.AppUtils.openUrl

class MaterialsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            return Intent(mContext, MaterialsActivity::class.java)
        }
    }

    var binding: ActivityMaterialsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_materials)

        val listMaterials = ArrayList<String>()
        val listMaterialUrls = ArrayList<String>()

        listMaterials.add(getString(R.string.material1))
        listMaterials.add(getString(R.string.material2))
        listMaterials.add(getString(R.string.material3))
        listMaterials.add(getString(R.string.material4))

        listMaterialUrls.add("http://welearn-project.eu/files/IO1_Report_WeLearn.pdf")
        listMaterialUrls.add("http://welearn-project.eu/files/WeLearn%20Intercultural%20Communication%20and%20Neighbourness%20Training%20Toolkit.pdf")
        listMaterialUrls.add("http://welearn-project.eu/files/IO3_Report_WeLearn-Online_Toolkit.pdf")
        listMaterialUrls.add("http://welearn-project.eu/files/WeLearn_TrainingToolkits-MethodTests-StudentEvaluations.pdf")

        val materialsAdapter =
            MaterialsAdapter(listMaterials, object : MaterialsAdapter.OnToolDescriptionClick {
                override fun onClick(position: Int) {
                    openUrl(mContext, listMaterialUrls[position])
                }
            })

        binding?.tvWeLearnWebsite?.text =
            Html.fromHtml(resources.getString(R.string.welearn_website), HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding?.tvWeLearnWebsite?.setOnClickListener {
            openUrl(mContext, "http://welearn-project.eu/results.php")
        }

        binding?.rvMaterials?.layoutManager = LinearLayoutManager(this)
        binding?.rvMaterials?.adapter = materialsAdapter
    }
}