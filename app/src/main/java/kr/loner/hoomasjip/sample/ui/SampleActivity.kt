package kr.loner.hoomasjip.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import kr.loner.hoomasjip.R
import kr.loner.hoomasjip.sample.util.toResColor
import kr.loner.shared.model.FakeBlog

class SampleActivity : ComponentActivity() {
    private val viewModel by viewModels<SampleViewModel> { SampleViewModelFactory() }
    private val loadingBar by lazy {
        findViewById<ProgressBar>(R.id.progress_loading)
    }
    private val itemGroupLayout by lazy {
        findViewById<LinearLayout>(R.id.linear_fakeBlogs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        viewModel.fakeBlogList.observe(this) { uiFakeBlogs ->
            if (uiFakeBlogs.loading) {
                loadingBar.visibility = View.VISIBLE
            } else {
                loadingBar.visibility = View.GONE
            }
            itemGroupLayout.addChildUiFromFakeBlog(uiFakeBlogs.data.orEmpty())
        }
    }

    private fun LinearLayout.addChildUiFromFakeBlog(fakeBlogList: List<FakeBlog>) {
        fakeBlogList.forEach {
            val item =
                LayoutInflater.from(this@SampleActivity).inflate(R.layout.item_sample, this, false)
            val title = item.findViewById<TextView>(R.id.tv_title)
            val content = item.findViewById<TextView>(R.id.tv_content)

            item.setBackgroundColor(it.color.toResColor())
            title.text = it.title
            content.text = it.desc
            addView(item, 0)
        }
    }
}
