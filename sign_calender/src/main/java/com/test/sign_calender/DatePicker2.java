package com.test.sign_calender;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 *  原版DatePicker
 */
public class DatePicker2 extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private MonthView monthView;// 月视图
    private TextView tvYear, tvMonth;// 年份 月份显示
    private TextView tvEnsure;// 确定按钮显示

    private static final int ID_tvMonth = 1;


    private DatePicker.OnDateSelectedListener onDateSelectedListener;// 日期多选后监听

    public DatePicker2(Context context) {
        this(context, null);
    }

    public DatePicker2(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 标题栏根布局
        RelativeLayout rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundResource(R.color.white);
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(30, rlTitlePadding, 30, rlTitlePadding);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundResource(R.color.white);
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        // 标题栏子元素布局参数
//        //左箭头
//        RelativeLayout.LayoutParams lpLeft =
//                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
//        lpLeft.addRule(RelativeLayout.CENTER_VERTICAL);
//        lpLeft.setMargins(40,0,0,0);
        //年
        RelativeLayout.LayoutParams lpYear =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpYear.addRule(RelativeLayout.CENTER_VERTICAL);
        lpYear.addRule(RelativeLayout.RIGHT_OF,ID_tvMonth);

        lpYear.setMargins(40,0,0,0);
        //月
        RelativeLayout.LayoutParams lpMonth =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMonth.addRule(RelativeLayout.CENTER_IN_PARENT);
//        //确定-->右箭头
//        RelativeLayout.LayoutParams lpEnsure =
//                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
//        lpEnsure.addRule(RelativeLayout.CENTER_VERTICAL);
//        lpEnsure.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lpEnsure.setMargins(0,0,40,0);

        // --------------------------------------------------------------------------------标题栏
        //左箭头
        // 年份显示
        tvYear = new TextView(context);
        tvYear.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvYear.setTextColor(getResources().getColor(R.color.green));

        // 月份显示
        tvMonth = new TextView(context);
        tvMonth.setId(ID_tvMonth);
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tvMonth.setTextColor(getResources().getColor(R.color.green));

        // 确定显示
//        tvEnsure = new TextView(context);
//        tvEnsure.setText(mLManager.titleEnsure());
//        tvEnsure.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        tvEnsure.setTextColor(mTManager.colorTitle());
//        tvEnsure.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != onDateSelectedListener) {
//                    onDateSelectedListener.onDateSelected(monthView.getDateSelected());
//                }
//            }
//        });
//        //右箭头
//        ImageView ivRight=new ImageView(context);
//        ivRight.setImageResource(R.drawable.sward);


//        rlTitle.addView(ivLeft,lpLeft);
        rlTitle.addView(tvYear, lpYear);
        rlTitle.addView(tvMonth, lpMonth);
//        rlTitle.addView(ivRight, lpEnsure);

        addView(rlTitle, llParams);

        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(getResources().getColor(R.color.blue));
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                Log.e("月",month+"");
                tvMonth.setText(mLManager.titleMonth()[month - 1]);
            }

            @Override
            public void onYearChange(int year) {
                Log.e("年",year+"");
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                tvYear.setText(tmp);
            }

            @Override
            public void onAllChange(int year, int month) {

            }
        });
        monthView.setOnDateScrollChangeListener(new MonthView.OnDateScrollChangeListener() {
            @Override
            public void scrollLeft(int year, int month) {
//                String str = "向左滑动=="+"年份="+year+"--月份=="+month;
//                Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void scrollRight(int year, int month) {
//                String str = "向右滑动=="+"年份="+year+"--月份=="+month;
//                Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void scrollTop(int year, int month) {
//                String str = "向上滑动=="+"年份="+year+"--月份=="+month;
//                Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void scrollBottom(int year, int month) {
//                String str = "向下滑动=="+"年份="+year+"--月份=="+month;
//                Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
            }
        });
        addView(monthView, llParams);
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        if (mode != DPMode.MULTIPLE) {
            tvEnsure.setVisibility(GONE);
        }
        monthView.setDPMode(mode);
    }

    /**
     * 节日标识
     * @param isFestivalDisplay
     */
    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    /**
     * 今天标识
     * @param isTodayDisplay
     */
    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    /**
     * 假期标识
     * @param isHolidayDisplay
     */
    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    /**
     * 补休标识
     * @param isDeferredDisplay
     */
    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(DatePicker.OnDatePickedListener onDatePickedListener) {
        if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException(
                    "Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(DatePicker.OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }

}
