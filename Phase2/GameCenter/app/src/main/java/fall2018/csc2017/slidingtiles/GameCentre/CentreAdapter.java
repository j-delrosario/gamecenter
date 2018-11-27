package fall2018.csc2017.slidingtiles.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTiles.StartingActivityST;
import fall2018.csc2017.slidingtiles.MatchingTiles.GameActivityMatch;
import fall2018.csc2017.slidingtiles.twozerofoureight.GameActivity2048;

/**
 * An adapter for game centre
 */
public class CentreAdapter extends RecyclerView.Adapter<CentreAdapter.myViewHolder> {

    /**
     * the Context
     */
    Context mContext;

    /**
     * A list tracking the user Data.
     */
    List<game_centre_item> mData;

    /**
     * initializes a game centre adapter
     * @param mContext
     * @param mData
     */
    public CentreAdapter(Context mContext, List<game_centre_item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_item, parent, false);
        return new myViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.profile_background.setImageResource(mData.get(position).getBackground());
        holder.tv_title.setText(mData.get(position).getProfileName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * A view holder.
     */
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView profile_background;
        TextView tv_title, tv_max_mark;

        public myViewHolder(View itemView) {
            super(itemView);
            profile_background = itemView.findViewById(R.id.card_background);
            tv_title = itemView.findViewById(R.id.card_title);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v){
            final Intent intent;
            switch (getAdapterPosition()){
                case 0:
                    intent =  new Intent(mContext, StartingActivityST.class);
                    break;

                case 1:
                    intent =  new Intent(mContext, GameActivityMatch.class);
                    break;

                case 2:
                    intent =  new Intent(mContext, GameActivity2048.class);
                    break;

                default:
                    intent =  new Intent(mContext, StartingActivityST.class);
                    break;
            }
            mContext.startActivity(intent);
        }
    }
}
