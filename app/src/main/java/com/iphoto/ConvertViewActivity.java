package com.iphoto;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.util.ILog;

import java.util.ArrayList;

/*
* List item 类型不同，convert view 如何工作？从log上看，先获取要显示的 item 类型，再从 recycle 中取相应的类型
* 的 item，内部应该根据不同类型，有不同的列表保存 item.
* */
public class ConvertViewActivity extends Activity {
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convert_view_activity_layout);
        mListView = (ListView)findViewById(R.id.convert_view_activity_list_view_id);

        ArrayList<ItemData> itemList = new ArrayList<ItemData>();
        itemList.add(createItem(ItemData.TYPE_SEPARATOR_UP, "国家", R.drawable.progress_inner));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "中国", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "巴基斯坦", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "俄罗斯", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "美国", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "德国", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "英国", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "意大利", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "以色列", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "韩国", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "朝鲜", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "柬埔寨", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "挪威", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "克罗地亚", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "乌克兰", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "日本", -1));
        itemList.add(createItem(ItemData.TYPE_SEPARATOR_DOWN, "国家", R.drawable.progress_outer));

        itemList.add(createItem(ItemData.TYPE_SEPARATOR_UP, "省份", R.drawable.progress_inner));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "江苏省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "湖北省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "上海市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "广东省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "山东省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "浙江省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "湖南省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "安徽省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "北京市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "四川省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "江西省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "广西省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "甘肃省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "海南省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "西藏自治区", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "黑龙江省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "辽宁省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "山西省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "陕西省", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "天津市", -1));
        itemList.add(createItem(ItemData.TYPE_SEPARATOR_DOWN, "省份", R.drawable.progress_outer));

        itemList.add(createItem(ItemData.TYPE_SEPARATOR_UP, "江苏的市", R.drawable.progress_inner));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "宿迁市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "盐城市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "连云港市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "徐州市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "南京市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "镇江市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "扬州市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "南通市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "苏州市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "无锡市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "淮安市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "常州市", -1));
        itemList.add(createItem(ItemData.TYPE_CONTENT, "泰州市", -1));
        itemList.add(createItem(ItemData.TYPE_SEPARATOR_DOWN, "江苏的市", R.drawable.progress_outer));

        ConvertAdapter adapter = new ConvertAdapter(this, itemList);
        mListView.setAdapter(adapter);
    }

    private ItemData createItem(int type, String content, int imageResId) {
        ItemData itemData = new ItemData();
        itemData.mType = type;
        itemData.mContent = content;
        itemData.mImageResId = imageResId;
        return itemData;
    }
}

class ConvertAdapter extends BaseAdapter {
    private ArrayList<ItemData> mItemList = null;
    private Context mContext = null;

    public ConvertAdapter(Context context, ArrayList<ItemData> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;

        if (convertView == null) {
            switch (mItemList.get(position).mType) {
                case ItemData.TYPE_SEPARATOR_UP: {
                    itemView = LayoutInflater.from(mContext).inflate(R.layout.convert_view_item1_layout, null);
                    ItemHolder1 holder1 = new ItemHolder1();
                    holder1.mImageView = (ImageView)itemView.findViewById(R.id.convert_view_item1_image_id);
                    holder1.mTextView = (TextView)itemView.findViewById(R.id.convert_view_item1_text_view_id);

                    holder1.mImageView.setImageResource(mItemList.get(position).mImageResId);
                    holder1.mTextView.setText(mItemList.get(position).mContent);
                    itemView.setTag(holder1);

                    ILog.d("create a item up separator, hashcode = " + itemView.hashCode());
                    break;
                }
                case ItemData.TYPE_CONTENT: {
                    itemView = LayoutInflater.from(mContext).inflate(R.layout.convert_view_item2_layout, null);
                    ItemHolder2 holder2 = new ItemHolder2();
                    holder2.mTextView = (TextView)itemView.findViewById(R.id.convert_view_item2_text_view_id);
                    holder2.mTextView.setText(mItemList.get(position).mContent);
                    itemView.setTag(holder2);

                    ILog.d("create a item content, hashcode = " + itemView.hashCode());
                    break;
                }
                case ItemData.TYPE_SEPARATOR_DOWN: {
                    itemView = LayoutInflater.from(mContext).inflate(R.layout.convert_view_item3_layout, null);
                    ItemHolder3 holder3 = new ItemHolder3();
                    holder3.mImageView = (ImageView)itemView.findViewById(R.id.convert_view_item3_image_id);
                    holder3.mTextView = (TextView)itemView.findViewById(R.id.convert_view_item3_text_view_id);
                    holder3.mImageView.setImageResource(mItemList.get(position).mImageResId);
                    holder3.mTextView.setText(mItemList.get(position).mContent);
                    itemView.setTag(holder3);

                    ILog.d("create a item down separator, hashcode = " + itemView.hashCode());
                    break;
                }
                default: {
                    throw new IllegalArgumentException("item type is error.");
                }
            }
        } else {
            itemView = convertView;
            ILog.d("convertView not null, hashcode = " + itemView.hashCode() + "type = " + mItemList.get(position).mType);

            switch (mItemList.get(position).mType) {
                case ItemData.TYPE_SEPARATOR_UP: {
                    ItemHolder1 holder1 = (ItemHolder1)itemView.getTag();
                    holder1.mImageView.setImageResource(mItemList.get(position).mImageResId);
                    holder1.mTextView.setText(mItemList.get(position).mContent);
                    break;
                }
                case ItemData.TYPE_CONTENT: {
                    ItemHolder2 holder2 = (ItemHolder2)itemView.getTag();
                    holder2.mTextView.setText(mItemList.get(position).mContent);
                    break;
                }
                case ItemData.TYPE_SEPARATOR_DOWN: {
                    ItemHolder3 holder3 = (ItemHolder3)itemView.getTag();
                    holder3.mImageView.setImageResource(mItemList.get(position).mImageResId);
                    holder3.mTextView.setText(mItemList.get(position).mContent);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("item type is error.");
                }
            }
        }

        return itemView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        ILog.d("getItemViewType position = " + position + "type = " + mItemList.get(position).mType);
        return mItemList.get(position).mType;
    }
}

class ItemData {
    public static final int TYPE_SEPARATOR_UP = 0;
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_SEPARATOR_DOWN = 2;

    public int mType;
    public String mContent;
    public int mImageResId;
}

class ItemHolder1 {
    public ImageView mImageView;
    public TextView mTextView;
}

class ItemHolder2 {
    public TextView mTextView;
}

class ItemHolder3 {
    public TextView mTextView;
    public ImageView mImageView;
}