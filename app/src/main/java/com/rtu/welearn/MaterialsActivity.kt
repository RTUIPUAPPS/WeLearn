package com.rtu.welearn

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rtu.welearn.databinding.ActivityMaterialsBinding

class MaterialsActivity : BaseActivity() {

    companion object {
        fun getIntent(mContext: Context): Intent {
            val intent = Intent(mContext, MaterialsActivity::class.java)
            return intent
        }
    }

    var binding: ActivityMaterialsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_materials)

        var listMaterials=ArrayList<String>()
        var listMaterialUrls=ArrayList<String>()

        listMaterials.add(getString(R.string.material1 ))
        listMaterials.add(getString(R.string.material2 ))
        listMaterialUrls.add("http://welearn-project.eu/files/IO1_Report_WeLearn.pdf")
        listMaterialUrls.add("http://welearn-project.eu/files/WeLearn%20Intercultural%20Communication%20and%20Neighbourness%20Training%20Toolkit.pdf")

        
        var materialsAdapter = MaterialsAdapter(listMaterials,object:MaterialsAdapter.OnToolDescriptionClick{
            override fun onClick(position: Int) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(listMaterialUrls[position])
                startActivity(i)
                //launchActivity(ToolDescriptionActivity.getIntent(baseContext, position))
            }
        })
        binding?.rvMaterials?.layoutManager = LinearLayoutManager(this)
        binding?.rvMaterials?.adapter = materialsAdapter
    }
}