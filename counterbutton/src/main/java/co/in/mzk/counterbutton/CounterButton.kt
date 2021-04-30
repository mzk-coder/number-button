package co.`in`.mzk.counterbutton
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import com.google.android.material.button.MaterialButton
import kotlin.math.roundToInt


class CounterButton constructor(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs),
    View.OnClickListener {


    private val plusIcon: ImageView
    private val addButton: MaterialButton
    private val removeButton: MaterialButton
    private val countText: TextView
    private val viewGroupBack: ConstraintLayout
    private var countChangeListener: OnCountChangeListener? = null

    private var primaryBackgroundColor = -1
    private var secondaryBackgroundColor = -1
    private var primaryTextColor = -1
    private var secondaryTextColor = -1

    private var primaryStrokeColor = -1
    private var secondaryStrokeColor = -1

    private var primaryStrokeWidth = 0
    private var secondaryStrokeWidth = 0

    private var defaultText : String? = "ADD"


    private var count: Int = 0



    init {
        inflate(context, R.layout.button_layout, this)

        plusIcon = findViewById(R.id.image_icon_add)
        countText = findViewById(R.id.tv_count)
        addButton = findViewById(R.id.btn_add)
        removeButton = findViewById(R.id.btn_remove)
        viewGroupBack = findViewById(R.id.button_background)


        addButton.setOnClickListener(this)
        removeButton.setOnClickListener(this)
        viewGroupBack.setOnClickListener(this)


        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CounterButton)

        defaultText = attributes.getString(R.styleable.CounterButton_defaultText)

        primaryBackgroundColor = attributes
            .getColor(R.styleable.CounterButton_primaryBackgroundColor, Color.BLACK)


        secondaryBackgroundColor = attributes
            .getColor(R.styleable.CounterButton_secondaryBackgroundColor, Color.WHITE)

        primaryTextColor = attributes
            .getColor(R.styleable.CounterButton_primaryTextColor, Color.WHITE)


        secondaryTextColor = attributes
            .getColor(R.styleable.CounterButton_secondaryTextColor, Color.BLACK)


        primaryStrokeColor = attributes
            .getColor(R.styleable.CounterButton_primaryStrokeColor, Color.WHITE)

        secondaryStrokeColor = attributes
            .getColor(R.styleable.CounterButton_secondaryStrokeColor, Color.BLACK)



        ImageViewCompat.setImageTintList(plusIcon, ColorStateList.valueOf(primaryTextColor))
        countText.setTextColor(primaryTextColor)

        addButton.rippleColor = ColorStateList.valueOf(secondaryBackgroundColor)
        removeButton.rippleColor = ColorStateList.valueOf(secondaryBackgroundColor)
        addButton.iconTint = ColorStateList.valueOf(secondaryTextColor)
        removeButton.iconTint = ColorStateList.valueOf(secondaryTextColor)



        try {

            primaryStrokeWidth = dp2Int(attributes.getDimension(
                R.styleable.CounterButton_primaryStrokeWidth,
                0f
            ))

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        try {

            secondaryStrokeWidth = dp2Int(
                attributes.getDimension(
                    R.styleable.CounterButton_secondaryStrokeWidth,
                    0f
                )
            )

        } catch (ex: Exception) {
            ex.printStackTrace()
        }


        (viewGroupBack.background as GradientDrawable?)?.setColor(primaryBackgroundColor)
        (viewGroupBack.background as GradientDrawable?)?.setStroke(
            primaryStrokeWidth,
            primaryStrokeColor
        )



        attributes.recycle()

    }


    fun setCount(count: Int) {
        this.count = count
        onCountChange()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_add) {
            count += 1
            if (countChangeListener != null) {
                countChangeListener!!.onClick(count, true, view)
            }

        } else if (view.id == R.id.btn_remove) {
            count -= 1
            if (countChangeListener != null) {
                countChangeListener!!.onClick(count, true, view)
            }
        } else {
            if (count == 0) {
                count += 1
                if (countChangeListener != null) {
                    countChangeListener!!.onClick(count, true, view)
                }
            }
        }

        onCountChange()


    }

    private fun onCountChange() {

        if (count > 0) {
            if (plusIcon.visibility == View.VISIBLE) {
                plusIcon.visibility = View.INVISIBLE
            }

            if (removeButton.visibility == View.INVISIBLE) {
                removeButton.visibility = View.VISIBLE
            }

            if (addButton.visibility == View.INVISIBLE) {
                addButton.visibility = View.VISIBLE
            }
            countText.text = "$count"
            (viewGroupBack.background as GradientDrawable?)?.setColor(secondaryBackgroundColor)
            (viewGroupBack.background as GradientDrawable?)?.setStroke(
                secondaryStrokeWidth,
                secondaryStrokeColor
            )

            countText.setTextColor(secondaryTextColor)
        } else {
            if (plusIcon.visibility == View.INVISIBLE) {
                plusIcon.visibility = View.VISIBLE
            }

            if (removeButton.visibility == View.VISIBLE) {
                removeButton.visibility = View.INVISIBLE
            }

            if (addButton.visibility == View.VISIBLE) {
                addButton.visibility = View.INVISIBLE
            }
            if (defaultText!=null){
                countText.text = defaultText
            }else{
                countText.text = "ADD"
            }


            (viewGroupBack.background as GradientDrawable?)?.setColor(primaryBackgroundColor)
            (viewGroupBack.background as GradientDrawable?)?.setStroke(primaryStrokeWidth, primaryStrokeColor)
            countText.setTextColor(primaryTextColor)
        }
    }

    fun getCount(): Int {
        return count
    }

    fun setCartListener(OnCountChangeListener: OnCountChangeListener) {
        countChangeListener = OnCountChangeListener
    }


    interface OnCountChangeListener {
        fun onClick(count: Int, added: Boolean, view: View)
    }

    private fun dp2Int(value: Float): Int {
//        val r: Resources = resources
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, r.displayMetrics)
//            .roundToInt()
        return value.roundToInt()
    }


}