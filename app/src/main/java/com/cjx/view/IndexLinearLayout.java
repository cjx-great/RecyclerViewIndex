package com.cjx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjx.R;

/**
 * Created by CJX on 2016-8-1.
 */
public class IndexLinearLayout extends LinearLayout {
    private Context context = null;
    private LayoutParams params = null;

    private OnIndexClickListener onIndexClickListener = null;

    public IndexLinearLayout(Context context) {
        this(context,null);
    }

    public IndexLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndexLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        setOrientation(VERTICAL);

        init();

        setClickable(true);
    }

    private void init(){
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,1);
        //添加箭头
        addView(addArrow());
        //添加字母
        for (char i = 'A'; i <= 'Z'; i++){
            String letter = i + "";
            addView(addLetter(letter));
        }
        //添加#号
        addView(addLetter("#"));
    }

    /**
     * 添加置顶的箭头
     * */
    private ImageView addArrow(){
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.top);

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onIndexClickListener != null){
                    onIndexClickListener.onArrow();
                }
            }
        });
        return imageView;
    }

    /**
     * 添加字母
     * */
    private TextView addLetter(final String letter){
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setText(letter);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onIndexClickListener != null){
                    onIndexClickListener.onLetter(letter);
                }
            }
        });
        return textView;
    }

    public interface OnIndexClickListener{
        void onLetter(String index);
        void onArrow();
    }

    public void setOnIndexClickListener(OnIndexClickListener onIndexClickListener){
        this.onIndexClickListener = onIndexClickListener;
    }
}
