package edu.aku.hassannaqvi.researchdayscoring.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import edu.aku.hassannaqvi.researchdayscoring.R;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ItemCommentBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ItemContentBinding;
import edu.aku.hassannaqvi.researchdayscoring.databinding.ItemSectionBinding;
import edu.aku.hassannaqvi.researchdayscoring.model.Presentation;

public class PresentationScoringAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_ITEM = 1;
    private final int VIEW_SECTION = 0;
    private final int VIEW_COMMENT = 2;
    Context context;
    ArrayList<Presentation> list;
    OnClickMinusPoint onClickMinusPoint;
    OnClickPlusPoint onClickPlusPoint;
    int points = 5;
    int initPoint = 1;

    public void setOnClickMinusPoint(OnClickMinusPoint onClickMinusPoint) {
        this.onClickMinusPoint = onClickMinusPoint;
    }

    public void setOnClickPlusPoint(OnClickPlusPoint onClickPlusPoint) {
        this.onClickPlusPoint = onClickPlusPoint;
    }

    public PresentationScoringAdapter(Context context, ArrayList<Presentation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;

        if (list.get(i) != null) {
            if (i == VIEW_SECTION) {
                ItemSectionBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_section, viewGroup, false);
                holder = new SectionViewHolder(bi);
            }
            if (i == VIEW_ITEM) {
                ItemContentBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_content, viewGroup, false);
                holder = new ContentViewHolder(bi);
            }
            if (i == VIEW_COMMENT) {
                ItemCommentBinding bi = DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_comment, viewGroup, false);
                holder = new CommentViewHolder(bi);
            }
        }


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {

        if (holder instanceof SectionViewHolder) {

            SectionViewHolder vh = (SectionViewHolder) holder;
            vh.bi.titleSection.setText(list.get(i).presentationReview);
        }
        if (holder instanceof ContentViewHolder) {

            final ContentViewHolder vh = (ContentViewHolder) holder;
            vh.bi.contentText.setText(list.get(i).presentationReview);
            final int pos = i;
            for (int j = 0; j < vh.bi.linearLayout.getChildCount(); j++) {
                final Button button = (Button) vh.bi.linearLayout.getChildAt(j);
                final int finalJ = j;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toggleTint(finalJ, vh.bi.linearLayout);
                        vh.bi.marksShow.setText(String.valueOf(finalJ));
                        list.get(pos).score = finalJ;
                    }
                });
            }

        }

        if (holder instanceof CommentViewHolder) {

            CommentViewHolder vh = (CommentViewHolder) holder;
            vh.bi.comment.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    list.get(i).comment = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    private void toggleTint(int finalJ, LinearLayout linearLayout) {

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            Button button = (Button) linearLayout.getChildAt(i);
            if (finalJ == i) {
                button.setBackground(context.getDrawable(R.drawable.star_button_yellow));
            } else {
                button.setBackground(context.getDrawable(R.drawable.round_button));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() - 2;
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder {

        ItemContentBinding bi;

        public ContentViewHolder(@NonNull ItemContentBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        ItemSectionBinding bi;

        public SectionViewHolder(@NonNull ItemSectionBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ItemCommentBinding bi;

        public CommentViewHolder(@NonNull ItemCommentBinding itemView) {
            super(itemView.getRoot());

            bi = itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isSection) {
            return VIEW_SECTION;
        } else if (!list.get(position).isSection && !list.get(position).isComment) {
            return VIEW_ITEM;
        } else if (!list.get(position).isSection && list.get(position).isComment) {
            return VIEW_COMMENT;
        } else {
            return 4;
        }

    }

    public ArrayList<Presentation> getList() {
        return list;
    }

    interface OnClickPlusPoint {

        void onClick(int i);
    }

    interface OnClickMinusPoint {

        void onClick(int i);
    }
}
