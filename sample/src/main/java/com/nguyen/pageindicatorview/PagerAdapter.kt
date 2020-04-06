import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.nguyen.pageindicatorview.FragmentOne
import com.nguyen.pageindicatorview.FragmentThree
import com.nguyen.pageindicatorview.FragmentTwo

class PagerAdapter internal constructor(@NonNull fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        lateinit var frag: Fragment
        when (position) {
            0 -> frag = FragmentOne()
            1 -> frag = FragmentTwo()
            2 -> frag = FragmentThree()
        }
        return frag
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title = ""
        when (position) {
            0 -> title = "One"
            1 -> title = "Two"
            2 -> title = "Three"
        }
        return title
    }
}