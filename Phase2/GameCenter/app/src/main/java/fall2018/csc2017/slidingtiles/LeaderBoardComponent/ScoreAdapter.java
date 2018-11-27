package fall2018.csc2017.slidingtiles.LeaderBoardComponent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fall2018.csc2017.slidingtiles.R;


/**
 * An adapter for the Score
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.myViewHolder>{

    /**
     * Current Context.
     */
    Context mContext;

    /**
     * List of score board items.
     */
    List<score_board_item> mData;

    /**
     * Initalize a Score Adapter.
     * @param mContext
     * @param mData
     */
    public ScoreAdapter(Context mContext, List<score_board_item> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ScoreAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.score_message, parent, false);
        return new ScoreAdapter.myViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.myViewHolder holder, int position) {
        String temp = new String();
        holder.tv_game_name.setText(temp.valueOf(position + 1)+ "." + mData.get(position).getGameName());
        holder.tv_game_score.setText(mData.get(position).getGameMark());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * A class to hold the view of the scorebaord.
     */
    public class myViewHolder extends RecyclerView.ViewHolder{

        /**
         * Teh textview of the game name and game score.
         */
        TextView tv_game_name, tv_game_score;

        public myViewHolder(View itemView) {
            super(itemView);
            tv_game_name = itemView.findViewById(R.id.gamename);
            tv_game_score = itemView.findViewById(R.id.gamescore);
            itemView.setClickable(true);
        }
    }
}
