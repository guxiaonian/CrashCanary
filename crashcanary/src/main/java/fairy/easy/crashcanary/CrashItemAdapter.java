package fairy.easy.crashcanary;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fairy.easy.crashcanary.crash.db.CrashBean;

import static android.content.Context.CLIPBOARD_SERVICE;


public class CrashItemAdapter extends BaseAdapter {
    private Context mContext;
    protected List<CrashBean> list;
    private ClipboardManager mClipboard;

    public CrashItemAdapter(Context context) {
        mContext = context;
        this.list = new ArrayList<>();
        mClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    }


    public void refresh(List<CrashBean> list) {
        this.list = list;
        Collections.reverse(this.list);
        notifyDataSetChanged();
    }

    public void cancel(){
        this.list.clear();
        notifyDataSetChanged();
    }

    public void cancelView(int postion){
        this.list.remove(postion);
        notifyDataSetChanged();
    }

    public void onLongClick(String data) {
        ClipData clipData = ClipData.newPlainText("Label",data);
        mClipboard.setPrimaryClip(clipData);
        Toast.makeText(mContext, "copy success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CrashBean crashBean = list.get(position);
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.crash_canary_list_item, null);
            holder = new Holder();
            holder.mParam = convertView.findViewById(R.id.crash_canary_param_text);
            holder.mTime = convertView.findViewById(R.id.crash_canary_time_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.mParam.setText(crashBean.getId() + "." + crashBean.getNormalError());
        holder.mTime.setText(crashBean.getStartTime());
        return convertView;
    }

    class Holder {
        private TextView mParam, mTime;
    }


}
