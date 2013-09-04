package com.hck.player.adpter;

import java.util.List;

import com.hck.myplayer.R;
import com.hck.player.ui.ShowTypeActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AllAdpter extends BaseExpandableListAdapter {
	public List<String> father;
	public List<List<String>> chilerd;
	private Context context;

	public AllAdpter(List<String> faList, List<List<String>> chList,
			Context context) {
		this.father = faList;
		this.chilerd = chList;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return chilerd.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = null;
		view = LayoutInflater.from(context).inflate(
				R.layout.all_expand_list_item, null);
		TextView textView = (TextView) view
				.findViewById(R.id.all_list_text_item_id);
		textView.setText(chilerd.get(groupPosition).get(childPosition));
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return chilerd.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return father.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return father.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.all_expand_list, null);
		TextView textView = (TextView) view.findViewById(R.id.all_list_text_id);
		textView.setText(father.get(groupPosition));
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		Intent intent = new Intent();
		intent.setClass(context, ShowTypeActivity.class);
		switch (groupPosition) {
		case 0: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 129 + "");
				intent.putExtra("name", "疯狂恶搞");
				break;
			case 1:
				intent.putExtra("id", 130 + "");
				intent.putExtra("name", "搞笑综艺");
				break;
			case 2:
				intent.putExtra("id", 131 + "");
				intent.putExtra("name", "原创搞笑");
				break;
			case 3:
				intent.putExtra("id", 132 + "");
				intent.putExtra("name", "爆笑宠物");
				break;
			case 4:
				intent.putExtra("id", 133 + "");
				intent.putExtra("name", "雷人囧事");
				break;
			case 5:
				intent.putExtra("id", 134 + "");
				intent.putExtra("name", "经典回顾");
				break;
			default:
				break;
			}
			break;
		}

		case 1: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 77 + "");
				intent.putExtra("name", "原创影视");
				break;
			case 1:
				intent.putExtra("id", 76 + "");
				intent.putExtra("name", "音乐动画");
				break;
			case 2:
				intent.putExtra("id", 78 + "");
				intent.putExtra("name", "火星搞笑");
				break;
			case 3:
				intent.putExtra("id", 79 + "");
				intent.putExtra("name", "校园作品");
				break;
			case 4:
				intent.putExtra("id", 80 + "");
				intent.putExtra("name", "网络红人");
				break;
			case 5:
				intent.putExtra("id", 81 + "");
				intent.putExtra("name", "拍客");
				break;
			default:
				break;
			}
			break;
		}

		case 2: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 9 + "");
				intent.putExtra("name", "明星八卦");
				break;
			case 1:
				intent.putExtra("id", 18 + "");
				intent.putExtra("name", "影视资讯");
				break;
			}
			break;

		}

		case 3: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 68 + "");
				intent.putExtra("name", "社会");
				break;
			case 1:
				intent.putExtra("id", 66 + "");
				intent.putExtra("name", "国内");
				break;
			case 2:
				intent.putExtra("id", 67 + "");
				intent.putExtra("name", "国际");
				break;
			case 3:
				intent.putExtra("id", 72 + "");
				intent.putExtra("name", "财富");
				break;
			case 4:
				intent.putExtra("id", 73 + "");
				intent.putExtra("name", "科技");
				break;
			}
			break;

		}
		
	
		case 4: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 8 + "");
				intent.putExtra("name", "美女车模");
				break;
			case 1:
				intent.putExtra("id", 98 + "");
				intent.putExtra("name", "评测报告");
				break;
			case 2:
				intent.putExtra("id", 7 + "");
				intent.putExtra("name", "汽车广告");
				break;
			case 3:
				intent.putExtra("id", 6 + "");
				intent.putExtra("name", "改装酷玩");
				break;
			case 4:
				intent.putExtra("id", 43 + "");
				intent.putExtra("name", "车型推荐");
				break;
			case 5:
				intent.putExtra("id", 5 + "");
				intent.putExtra("name", "新车速递");
				break;
			}
			break;
		}

		case 5: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 36 + "");
				intent.putExtra("name", "篮球天地");
				break;
			case 1:
				intent.putExtra("id", 37 + "");
				intent.putExtra("name", "足球世界");
				break;
			case 2:
				intent.putExtra("id", 41 + "");
				intent.putExtra("name", "综合体育");
				break;
			case 3:
				intent.putExtra("id", 38 + "");
				intent.putExtra("name", "极限运动");
				break;
			case 4:
				intent.putExtra("id", 50 + "");
				intent.putExtra("name", "武术摔角");
				break;
			case 5:
				intent.putExtra("id", 48 + "");
				intent.putExtra("name", "美女花边");
				break;
			}
			break;
		}

		case 6: {
			switch (childPosition) {
			case 0:
				intent.putExtra("id", 60 + "");
				intent.putExtra("name", "网络游戏");
				break;
			case 1:
				intent.putExtra("id", 61 + "");
				intent.putExtra("name", "电子竞技");
				break;
			case 2:
				intent.putExtra("id", 62 + "");
				intent.putExtra("name", "单机电玩");
				break;
			case 3:
				intent.putExtra("id", 63 + "");
				intent.putExtra("name", "游戏达人");
				break;
			case 4:
				intent.putExtra("id", 64 + "");
				intent.putExtra("name", "工会战队");
				break;
			}
		}
		}
		context.startActivity(intent);
		return true;
	}

}
