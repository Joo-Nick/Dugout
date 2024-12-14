import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// RecyclerView 항목 간 위아래 간격만 설정하는 ItemDecoration 클래스
class VerticalItemSpacingDecoration(private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        // 위와 아래 간격만 설정
        outRect.top = verticalSpacing
        outRect.bottom = verticalSpacing
    }
}