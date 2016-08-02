package com.cjx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjx.R;
import com.cjx.bean.ItemTypeAndData;
import com.cjx.util.SpellComparator;
import com.cjx.util.SpellUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by CJX on 2016-8-1.
 *
 * 传入的是纯粹的数据
 * 需要用到数据拼音来进行排序,分组
 * 最终显示结果是拼音首字母及相应数据
 *
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context = null;
    private LayoutInflater inflater = null;

    private String[] datas = null;
    // datas转换成拼音
    private List<String> datasToSpellList;
    // 分组字母List
    private List<String> letterList;
    // 最终结果(包含分组的字母)
    private List<ItemTypeAndData> resultList;

    //分组字母,数据(区分Item)
    public enum ITEM_TYPE {
        ITEM_TYPE_SPELL,
        ITEM_TYPE_DATA
    }

    public RecyclerAdapter(Context context, String[] datas) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.datas = datas;

        handleDatas();
    }

    /**
     * 对数据进行排序和分类
     * */
    private void handleDatas(){
        datasToSpellList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < datas.length; i++) {
            //转换为拼音
            String spell = SpellUtils.convertToSpell(datas[i]);
            // key是转换后的值，value是转换之前的值
            // 列表中最终显示的是中文，而不是拼音，拼音只是用来排序和分组用
            map.put(spell,datas[i]);
            datasToSpellList.add(spell);
        }

        //排序
        Collections.sort(datasToSpellList, new SpellComparator());

        letterList = new ArrayList<>();
        resultList = new ArrayList<>();

        for (int i = 0; i < datasToSpellList.size(); i++) {
            String spell = datasToSpellList.get(i);
            //大写首字母
            String spellStart = (spell.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            //将数据首字母添加至list
            if (!letterList.contains(spellStart)){
                // 是字母
                if (spellStart.hashCode() >= "A".hashCode() && spellStart.hashCode() <= "Z".hashCode()) {
                    letterList.add(spellStart);
                    resultList.add(new ItemTypeAndData(spellStart, ITEM_TYPE.ITEM_TYPE_SPELL.ordinal()));
                } else {
                    if (!letterList.contains("#")) {
                        letterList.add("#");
                        resultList.add(new ItemTypeAndData("#", ITEM_TYPE.ITEM_TYPE_SPELL.ordinal()));
                    }
                }
            }

            resultList.add(new ItemTypeAndData(map.get(spell), ITEM_TYPE.ITEM_TYPE_DATA.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TYPE.ITEM_TYPE_SPELL.ordinal()){
            view = inflater.inflate(R.layout.divider_item,parent,false);
            return new DividerViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.recycler_item,parent,false);
            return new ContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentViewHolder){
            ((ContentViewHolder) holder).imageView.setImageResource(R.mipmap.ic_launcher);
            ((ContentViewHolder) holder).textView.setText(resultList.get(position).getContent());
        }else if (holder instanceof DividerViewHolder){
            ((DividerViewHolder) holder).textView.setText(resultList.get(position).getContent());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public int getScrollPosition(String letter) {
        if (letterList.contains(letter)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getContent().equals(letter)) {
                    return i;
                }
            }
        }

        return -1; // -1不会滑动
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView = null;
        TextView textView = null;

        public ContentViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_imag);
            textView = (TextView) itemView.findViewById(R.id.item_content);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder{
        TextView textView = null;

        public DividerViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.divider_content);
        }
    }

}
