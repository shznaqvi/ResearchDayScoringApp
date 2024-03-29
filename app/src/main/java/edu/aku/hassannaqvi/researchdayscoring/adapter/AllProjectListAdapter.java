package edu.aku.hassannaqvi.researchdayscoring.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.animation.Animations;
import edu.aku.hassannaqvi.researchdayscoring.contracts.ProjectsContract;
import edu.aku.hassannaqvi.researchdayscoring.core.DatabaseHelper;
import edu.aku.hassannaqvi.researchdayscoring.core.MainApp;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ListItemBinding;

public class AllProjectListAdapter extends RecyclerView.Adapter<AllProjectListAdapter.ViewHolder> {

    Context context;
    List<ProjectsContract> list;

    OnItemClickListener clickListener;
    DatabaseHelper db;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public AllProjectListAdapter(Context context, List<ProjectsContract> list) {
        this.list = list;
        this.context = context;
        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        ListItemBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item, viewGroup, false);

        return new ViewHolder(bi);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        if (list.get(i) != null) {
            holder.bi.projectTitle.setText(list.get(i).getTitle());
            holder.bi.theme.setText(list.get(i).getTheme());

            holder.bi.authorFirstLetter.setText(list.get(i).getProj_id().split("_")[0] + " " + list.get(i).getProj_id().split("_")[1]);
            holder.bi.author.setText(list.get(i).getAuthor());

          /*  holder.bi.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayout(!list.get(i).isExpanded(), v, holder.bi.layoutExpand);
                    list.get(i).setExpanded(show);
                    holder.bi.authorName.setText(list.get(i).getAuthor());
                    holder.bi.projectAbstract.setText(list.get(i).getAbstract());
                    holder.bi.projectTheme.setText(list.get(i).getTheme());

                }
            });*/

            if (!db.isDataExists(list.get(i).getProj_id(), MainApp.userName)) {
                holder.bi.checkMark.setVisibility(View.GONE);
                holder.bi.totalMarks.setVisibility(View.GONE);
                holder.bi.projectTitle.setTextColor(Color.parseColor("#000000"));
                holder.bi.parent.setBackgroundColor(Color.parseColor("#ffffff"));
            } else {
                holder.bi.totalMarks.setText(db.getScore(list.get(i).getProj_id(), MainApp.userName));
                holder.bi.checkMark.setVisibility(View.VISIBLE);
                holder.bi.totalMarks.setVisibility(View.VISIBLE);
                holder.bi.projectTitle.setTextColor(Color.argb(153, 153, 153, 153));
                holder.bi.parent.setBackgroundColor(Color.parseColor("#d6d6d6d6"));
            }

            holder.bi.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!db.isDataExists(list.get(i).getProj_id(), MainApp.userName)) {
                        clickListener.OnItemClick(list.get(i));
                    } else {
                        Toast.makeText(context, "This project has already been scored!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private boolean toggleLayout(boolean isExpanded, View v, LinearLayout layoutExpand) {
        Animations.toggleArrow(v, isExpanded);
        if (isExpanded) {
            Animations.expand(layoutExpand);
        } else {
            Animations.collapse(layoutExpand);
        }
        return isExpanded;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding bi;

        public ViewHolder(@NonNull ListItemBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(ProjectsContract contract);
    }

}
