
package com.example.vinh.wumpus_hvinh;

import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int SIZE = 4;
    private static boolean GOLD = true;
    private static boolean WUMPUSISDIE = false;
    private static int POSITIONOFAGENT = 0;
    private static int NEXTDESTINATONLOWDANGER = 0;
    private static int POSITIONWHENAGENTDIE = 0;
    private static boolean SHOWMAP = true;
    ImageView imageView;
    Square square;
    List<ImageView> imageViewList;
    List<Square> squareLists;
    Random random;
    Button btnLeft, btnRight, btnUp, btnDown, btnAuto, btnRestart, btnShowMap, btnClick;
    EditText edtInputSize;
    LinearLayout linearLayoutRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLayout();
        setBreezeandStench(squareLists);
        setContentView(linearLayoutRoot);
    }
    private void createLayout()
    {
        linearLayoutRoot = new LinearLayout(this);
        linearLayoutRoot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayoutRoot.setOrientation(LinearLayout.VERTICAL);
        edtInputSize = new EditText(this);
        edtInputSize.setHint("Please Input Size");
        linearLayoutRoot.setWeightSum(SIZE);

        imageViewList = new ArrayList<ImageView>();
        squareLists = new ArrayList<Square>();

        for(int i = 0; i <SIZE; i++)
        {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j <SIZE; j++)
            {
                imageView = new ImageView(this);
                imageView.setBackgroundResource(R.drawable.background);
                linearLayout.addView(imageView);
                imageViewList.add(imageView);
            }
            linearLayoutRoot.addView(linearLayout);
        }

        for(int i = 0; i < SIZE*SIZE;i++)
        {
            square = new Square(i);
            squareLists.add(square);
        }
        random(imageViewList, squareLists);
        //map4square(imageViewList,squareLists);
        // dieu kien khoi tao game
        rulesofGame();
        setVisibleImageViewList();


        btnLeft = new Button(this);
        btnLeft.setText("LEFT");
        btnRight = new Button(this);
        btnRight.setText("RIGHT");
        btnUp = new Button(this);
        btnUp.setText("UP");
        btnDown = new Button(this);
        btnDown.setText("DOWN");
        btnAuto = new Button(this);
        btnAuto.setText("AUTO");
        btnRestart = new Button(this);
        btnRestart.setText("Restart");
        btnShowMap = new Button(this);
        btnShowMap.setText("Show map");
        btnClick = new Button(this);
        btnClick.setText("Click");
        setActionforButton();
        LinearLayout linearLayoutButton = new LinearLayout(this);
        linearLayoutButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayoutButton.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayoutButton.addView(btnLeft);
//        linearLayoutButton.addView(btnRight);
//        linearLayoutButton.addView(btnUp);
//        linearLayoutButton.addView(btnDown);
        linearLayoutButton.addView(btnAuto);
        linearLayoutButton.addView(btnRestart);
        linearLayoutButton.addView(btnShowMap);
        linearLayoutButton.addView(edtInputSize);
        linearLayoutButton.addView(btnClick);
        linearLayoutRoot.addView(linearLayoutButton);
    }
    private void map4square(List<ImageView> imageViewList, List<Square>squareLists)
    {
        POSITIONOFAGENT = 12;
        squareLists.get(POSITIONOFAGENT).setAgent(true);
        squareLists.get(2).setHole(true);
        squareLists.get(4).setWumpus(true);
        squareLists.get(5).setGold(true);
        squareLists.get(7).setHole(true);
        squareLists.get(14).setHole(true);
        for(int i = 0; i<squareLists.size();i++)
        {
            if(squareLists.get(i).isAgent())
            {
                imageViewList.get(i).setImageResource(R.drawable.agent);
            }
            if(squareLists.get(i).isHole())
            {
                imageViewList.get(i).setImageResource(R.drawable.hole);
            }
            if(squareLists.get(i).isWumpus())
            {
                imageViewList.get(i).setImageResource(R.drawable.wumpus);
            }
            if(squareLists.get(i).isGold())
            {
                imageViewList.get(i).setImageResource(R.drawable.gold);
            }
        }
        for(int i = 0; i <squareLists.size();i++)
        {
            imageViewList.get(i).setEnabled(false);
        }
    }
    private void random(List<ImageView> imageViewList,List<Square> squareLists)
    {
        int count = 0;
        int numberofHole = Math.round((float)(SIZE*SIZE*15/100)) + 1;
        random = new Random();
        if(SIZE%2 == 0)
        {
            POSITIONOFAGENT = 0;
        }
        else {
            POSITIONOFAGENT = (((SIZE*SIZE)+1)/2) -1;
        }
        imageViewList.get(POSITIONOFAGENT).setImageResource(R.drawable.agent);
        squareLists.get(POSITIONOFAGENT).setAgent(true);
        for(int i = 0; i < 500; i++)
        {
            int k = random.nextInt(imageViewList.size()-1)+1;
//            Log.i("COUNT", String.valueOf(count));
//            Log.i("RAMDOM",String.valueOf(k));
            if(count < numberofHole)
            {
                if(squareLists.get(k).isAgent() || squareLists.get(k).isHole())
                {
                }
                else {
//                    Log.i("HOLE",String.valueOf(k));
                    squareLists.get(k).setHole(true);
                    imageViewList.get(k).setImageResource(R.drawable.hole);
                    count++;
                }
            } else if(count == numberofHole){
                if(squareLists.get(k).isAgent() || squareLists.get(k).isHole())
                {

                }
                else {
//                    Log.i("GOLD",String.valueOf(k));
                    squareLists.get(k).setGold(true);
                    imageViewList.get(k).setImageResource(R.drawable.gold);
                    count++;
                }
            } else if(count == numberofHole+1)
            {
                if(squareLists.get(k).isAgent() || squareLists.get(k).isHole() || squareLists.get(k).isGold())
                {

                }
                else {
//                    Log.i("WUMPUS",String.valueOf(k));
                    squareLists.get(k).setWumpus(true);
                    imageViewList.get(k).setImageResource(R.drawable.wumpus);
                    return;
                }
            }
        }
    }
    private void setActionforButton()
    {
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLeft(imageViewList,squareLists);
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToRight(imageViewList,squareLists);
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveUp(imageViewList,squareLists);
            }
        });
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveDown(imageViewList,squareLists);
            }
        });
        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                automaticmove(squareLists);
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GOLD = true;
                WUMPUSISDIE = false;
                SHOWMAP = true;
                recreate();
            }
        });
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SHOWMAP == false)
                {
                    SHOWMAP = true;
                    setDisableImageViewList();
                }
                else {
                    SHOWMAP = false;
                    setEnableImageViewList();
                }
            }
        });
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(edtInputSize.getText().toString())<13 && Integer.parseInt(edtInputSize.getText().toString())>3 )
                {
                    SIZE = Integer.parseInt(edtInputSize.getText().toString());
                    recreate();
                }
                else {
                    Toast.makeText(MainActivity.this, "Please input size between 4 to 12", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void moveToLeft(List<ImageView> imageViewList, List<Square> squareLists)
    {
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {
                if(i%SIZE != 0)
                {
                    squareLists.get(i).setAgent(false);
                    squareLists.get(i-1).setAgent(true);
                    imageViewList.get(i).setImageResource(android.R.color.transparent);
                    imageViewList.get(i-1).setImageResource(R.drawable.agent);
                    checkAgentdie(squareLists.get(i-1),i-1);
                    setAllStatus(i-1);
                    imageViewList.get(i-1).setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
    private void moveToRight(List<ImageView> imageViewList, List<Square> squareLists)
    {
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {
                if((i+1) % SIZE != 0)
                {
                    squareLists.get(i).setAgent(false);
                    squareLists.get(i+1).setAgent(true);
                    imageViewList.get(i).setImageResource(android.R.color.transparent);
                    imageViewList.get(i+1).setImageResource(R.drawable.agent);
                    checkAgentdie(squareLists.get(i+1),i+1);
                    setAllStatus(i+1);
                    imageViewList.get(i+1).setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
    private void moveUp(List<ImageView> imageViewList, List<Square> squareLists)
    {
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {
                if(i - SIZE >= 0)
                {
                    squareLists.get(i).setAgent(false);
                    squareLists.get(i-SIZE).setAgent(true);
                    imageViewList.get(i).setImageResource(android.R.color.transparent);
                    imageViewList.get(i-SIZE).setImageResource(R.drawable.agent);
                    checkAgentdie(squareLists.get(i-SIZE),i-SIZE);
                    setAllStatus(i-SIZE);
                    imageViewList.get(i-SIZE).setVisibility(View.VISIBLE);
                }
                break;
            }
        }

    }
    private void moveDown(List<ImageView> imageViewList, List<Square> squareLists)
    {
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {
                if(i + SIZE < SIZE*SIZE)
                {
                    squareLists.get(i).setAgent(false);
                    squareLists.get(i+SIZE).setAgent(true);
                    imageViewList.get(i).setImageResource(android.R.color.transparent);
                    imageViewList.get(i+SIZE).setImageResource(R.drawable.agent);
                    checkAgentdie(squareLists.get(i+SIZE), i+SIZE);
                    setAllStatus(i+SIZE);
                    imageViewList.get(i+SIZE).setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    }
    private void setBreezeandStench(List<Square>squareLists)
    {
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isWumpus())
            {
                if(checkmoveLeft(i) && !squareLists.get(i-1).isHole())
                    squareLists.get(i-1).setStench(true);
                if(checkmoveRight(i) && !squareLists.get(i+1).isHole())
                    squareLists.get(i+1).setStench(true);
                if(checkmoveUp(i) && !squareLists.get(i-SIZE).isHole())
                    squareLists.get(i-SIZE).setStench(true);
                if(checkmoveDown(i) && !squareLists.get(i+SIZE).isHole())
                    squareLists.get(i+SIZE).setStench(true);
            }
            if(squareLists.get(i).isHole())
            {
                if(checkmoveLeft(i) && !squareLists.get(i-1).isHole())
                    squareLists.get(i-1).setBreeze(true);
                if (checkmoveRight(i) &&!squareLists.get(i+1).isHole())
                    squareLists.get(i+1).setBreeze(true);
                if(checkmoveUp(i) && !squareLists.get(i-SIZE).isHole())
                    squareLists.get(i-SIZE).setBreeze(true);
                if(checkmoveDown(i) && !squareLists.get(i+SIZE).isHole())
                    squareLists.get(i+SIZE).setBreeze(true);
            }
        }
    }
    private void setAllStatus(int i)
    {
//        for(int i = 0; i < squareLists.size(); i++)
//        {
            if(squareLists.get(i).isBreeze() && squareLists.get(i).isStench())
                imageViewList.get(i).setBackgroundResource(R.drawable.bgwindandmonster);
            if(squareLists.get(i).isBreeze() && !squareLists.get(i).isStench())
                imageViewList.get(i).setBackgroundResource(R.drawable.bgwind);
            if(squareLists.get(i).isStench() && !squareLists.get(i).isBreeze())
            {
                imageViewList.get(i).setBackgroundResource(R.drawable.bgmonter);
            }
            if(!squareLists.get(i).isStench() && !squareLists.get(i).isBreeze())
            {
                imageViewList.get(i).setBackgroundResource(R.drawable.background);
            }
 //       }
    }
    private boolean checkmoveLeft(int i)
    {
        if(i -1 >= 0 && (i % SIZE != 0))
            return true;
        return false;
    }
    private boolean checkmoveRight(int i)
    {
        if(i + 1 < SIZE*SIZE && (i+1) % SIZE != 0 )
            return true;
        return false;
    }
    private boolean checkmoveUp(int i)
    {
        if(i - SIZE >= 0)
            return true;
        return false;
    }
    private boolean checkmoveDown(int i)
    {
        if(i + SIZE <= (SIZE*SIZE) -1)
            return true;
        return false;
    }
    private void automaticmove(List<Square>squareLists)
    {
        int destination;
        tickSafeandDanger(squareLists);
        Agentthink();
        if(WUMPUSISDIE == true)
        {
            setWhenWumpusdie();
            WUMPUSISDIE = false;
            return;
        }
        for(int i = 0; i <squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent() && squareLists.get(i).isGold())
            {
                squareLists.get(i).setGold(false);
                GOLD = false;
                Toast.makeText(MainActivity.this,"GOLD IS FOUND",Toast.LENGTH_LONG).show();
            }
        }
        if(GOLD == false)
        {
            destination = POSITIONOFAGENT;
        }
        else {
            destination = findnextPointSafe();
        }
        Log.i("NEXT DESTINATON",String.valueOf(destination));
        Log.i("MIN DISTANCE",String.valueOf(minDistance(destination)));
        moveShorterPath(destination);
    }
    private int minDistance(int destination)
    {
        int sourceInt = 0;
        QItem now;
        boolean[] booleans = new boolean[squareLists.size()];
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent()) {
                sourceInt = i;
                booleans[i] = true;
            }
            else if(squareLists.get(i).isSafe() && squareLists.get(i).isAgent() == false)
            {
                booleans[i] = false;
            } else if(squareLists.get(i).isVisited() && squareLists.get(i).isAgent() == false)
            {
                booleans[i] = false;
            }
            else if(i == NEXTDESTINATONLOWDANGER)
            {
                booleans[i] = false;
            }
            else
                booleans[i] = true;
        }
        if(sourceInt == destination)
            return -1 ;
        QItem source = new QItem(sourceInt,0);
        QItem newPoint;
        Queue<QItem> queue = new LinkedList();
        queue.add(source);
        while(!queue.isEmpty())
        {
            now = queue.remove();
            if(now.getNow() == destination)
                return now.getCountStep();

            // moving up
            if(checkmoveUp(now.getNow()))
            {
                if(booleans[now.getNow() - SIZE] == false)
                {
                    newPoint = new QItem(now.getNow()-SIZE,now.getCountStep()+1);
                    queue.add(newPoint);
                    booleans[now.getNow() - SIZE] = true;
                }
            }
            // moving down
            if(checkmoveDown(now.getNow()))
            {
                if(booleans[now.getNow() + SIZE] == false)
                {
                    newPoint = new QItem(now.getNow()+SIZE,now.getCountStep()+1);
                    queue.add(newPoint);
                    booleans[now.getNow() + SIZE] = true;
                }
            }
            if(checkmoveLeft(now.getNow()))
            {
                if(booleans[now.getNow() - 1] == false)
                {
                    newPoint = new QItem(now.getNow()-1,now.getCountStep()+1);
                    queue.add(newPoint);
                    booleans[now.getNow() - 1] = true;
                }
            }
            if(checkmoveRight(now.getNow()))
            {
                if(booleans[now.getNow() + 1] == false)
                {
                    newPoint = new QItem(now.getNow()+1,now.getCountStep()+1);
                    queue.add(newPoint);
                    booleans[now.getNow() + 1] = true;
                }
            }
        }
        return -1;
    }
    private void moveShorterPath(int destination)
    {
        int source = 0;
        int now = 0;
        boolean[] booleans = new boolean[squareLists.size()];
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent()) {
                source = i;
                booleans[i] = true;
            }
            else if(squareLists.get(i).isSafe() && squareLists.get(i).isAgent() == false)
            {
                booleans[i] = false;
            } else if(squareLists.get(i).isVisited() && squareLists.get(i).isAgent() == false)
            {
                booleans[i] = false;
            }
            else
                booleans[i] = true;
        }
        if(source == destination)
            return ;
        Queue<Integer> queue = new LinkedList();
        queue.add(destination);
        while(!queue.isEmpty())
        {
            now = queue.remove();
            // source found
            if(checkmoveLeft(now))
            {
                if(squareLists.get(now -1).isAgent())
                {
                    moveToRight(imageViewList,squareLists);
                    return;
                }
            }
            if(checkmoveDown(now))
            {
                if(squareLists.get(now+SIZE).isAgent())
                {
                    moveUp(imageViewList, squareLists);
                    return ;
                }
            }
            if(checkmoveUp(now)){
                if(squareLists.get(now-SIZE).isAgent())
                {
                    moveDown(imageViewList, squareLists);
                    return ;
                }
            }
            if(checkmoveRight(now))
            {
                if (squareLists.get(now+1).isAgent())
                {
                    moveToLeft(imageViewList,squareLists);
                    return ;
                }
            }
            // moving up
            if(checkmoveUp(now))
            {
                if(booleans[now - SIZE] == false)
                {
                    queue.add(now-SIZE);
                    booleans[now - SIZE] = true;
                }
            }
            // moving down
            if(checkmoveDown(now))
            {
                if(booleans[now + SIZE] == false)
                {
                    queue.add(now + SIZE);
                    booleans[now + SIZE] = true;
                }
            }
            if(checkmoveLeft(now))
            {
                if(booleans[now - 1] == false)
                {
                    queue.add(now-1);
                    booleans[now - 1] = true;
                }
            }
            if(checkmoveRight(now))
            {
                if(booleans[now + 1] == false)
                {
                    queue.add(now+1);
                    booleans[now + 1] = true;
                }
            }
        }
    }
    private int findnextPointSafe()
    {
        int destinationPoint = POSITIONOFAGENT;
        ArrayList<Integer> arrayList = new ArrayList();
        ArrayList<Integer> arrayListDangerHole = new ArrayList();
        ArrayList<Integer> arrayListDangerWumpus = new ArrayList();
        ArrayList<Integer> arrayList1 = new ArrayList();
        int tempDes = 0;
        int tempDistance = 0;
        int tempDistance1 = 0;
        int tempDistance2 = 100;
        int tempDistance3 = 100;
        int tempDestinationNearest = 0;
        int tempDangerHole = 0;
        for(int i = 0 ; i<squareLists.size();i++)
        {
            if(squareLists.get(i).isAgent())
            {
                for (int j = 0 ; j < squareLists.size(); j++)
                {
                    if(squareLists.get(j).isSafe() == false)
                    {

                    }
                    else if(squareLists.get(j).isSafe() && !squareLists.get(j).isVisited())
                    {
                        arrayList.add(j);
                    }
                    if(squareLists.get(j).getDangerHole() < 3 && squareLists.get(j).getDangerHole() >0 && squareLists.get(j).getDangerWumpus() == 0 && !squareLists.get(j).isVisited())
                    {
                        arrayListDangerHole.add(j);
                    }
                    if(squareLists.get(j).getDangerWumpus() <3 && squareLists.get(j).getDangerWumpus() >0 && squareLists.get(j).getDangerHole() == 0 && !squareLists.get(j).isVisited())
                    {
                        arrayListDangerWumpus.add(j);
                    }
                    else
                    {
                        arrayList1.add(j);
                    }
                }
                break;
            }
        }
//        for(int i = 0; i < arrayListDangerHole.size(); i++)
//        {
//            Log.i("ARR DANGERHOLE ",String.valueOf(arrayListDangerHole.get(i)));
//        }
        if(arrayList.size() != 0 || arrayListDangerHole.size()!= 0 || arrayListDangerWumpus.size()!= 0|| arrayList1.size()!=0)
        {
            if(arrayList.size()==1)
            {
                return destinationPoint = arrayList.get(0);
            }
            if(arrayList.size()>1)
            {
                for(int i = 0; i < arrayList.size(); i++)
                {
                    tempDes = arrayList.get(i);
                    tempDistance1 = minDistance(tempDes);
//                  Log.i("POINT SAFE:" + String.valueOf(tempDes),String.valueOf(tempDistance1));
                    if(tempDistance1 < tempDistance2)
                    {
                        tempDistance2 = tempDistance1;
                        tempDestinationNearest = arrayList.get(i);
                        //Log.i("CHOOSEN POINT SAFE:"+ String.valueOf(tempVitrigannhat), String.valueOf(tempDistance2));
                    }
                }
                return destinationPoint = tempDestinationNearest;
            }
            if(arrayListDangerHole.size() == 1)
            {
                return  destinationPoint = arrayListDangerHole.get(0);
            }
            if(arrayListDangerHole.size() > 1)
            {
                int tempa = 3;
                for(int i = 0; i < arrayListDangerHole.size(); i++)
                {
                    tempDangerHole = squareLists.get(arrayListDangerHole.get(i)).getDangerHole();
                    if(tempDangerHole < tempa && tempDangerHole != 0)
                    {
                        tempa = tempDangerHole;
                    }
                }
                for(int i = 0; i <arrayListDangerHole.size(); i++)
                {
                    if(squareLists.get(arrayListDangerHole.get(i)).getDangerHole() == tempa)
                    {
                        //Log.i("CHOOSEN POINT HOLE", String.valueOf(arrayListDangerHole.get(i)));
                        tempDes = arrayListDangerHole.get(i);
                        NEXTDESTINATONLOWDANGER = tempDes;
                        tempDistance = minDistance(NEXTDESTINATONLOWDANGER); // tai sao bang -1
                        Log.i("DISTANCE", String.valueOf(tempDistance));
                        if(tempDistance < tempDistance3)
                        {
                            tempDistance3 = tempDistance;
                            tempDestinationNearest = tempDes;
                        }
                    }
                }
                if(tempa == 1)
                    return destinationPoint = tempDestinationNearest;
                if(tempa == 2)
                {
                    if(!arrayListDangerWumpus.isEmpty())
                    {
                        for (int i = 0; i < arrayListDangerWumpus.size(); i++)
                        {
                            if(squareLists.get(arrayListDangerWumpus.get(i)).getDangerWumpus() == 1)
                            {
                                return destinationPoint = arrayListDangerWumpus.get(i);
                            }
                        }
                    }
                    return destinationPoint = tempDestinationNearest;
                }
            }
            if(!arrayListDangerWumpus.isEmpty())
            {
                for (int i = 0; i < arrayListDangerWumpus.size(); i++)
                {
                    if(squareLists.get(arrayListDangerWumpus.get(i)).getDangerWumpus() == 1)
                    {
                        return destinationPoint = arrayListDangerWumpus.get(i);
                    }
                }
                for (int i = 0; i < arrayListDangerWumpus.size(); i++)
                {
                    if(squareLists.get(arrayListDangerWumpus.get(i)).getDangerWumpus() == 2)
                    {
                        return destinationPoint = arrayListDangerWumpus.get(i);
                    }
                }
            }
            if(!arrayList1.isEmpty())
            {
                for (int i = 0; i <arrayList1.size(); i++)
                {
                    return destinationPoint = arrayList1.get(i);
                }
            }
        }
        else {
            return destinationPoint = POSITIONOFAGENT;
        }
        return destinationPoint = POSITIONOFAGENT;
    }
    private void tickSafeandDanger(List<Square>squareLists)
    {
        for(int i = 0; i < squareLists.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {
                if(squareLists.get(i).isVisited() == false)
                {
                    tickwhenAgentnovisite(i); // sau khi set độ nguy hiểm thì vị trí đó được visit
                    squareLists.get(i).setVisited(true);
                }
                break;
            }
        }
    }
    private void Agentthink()
     {
        for(int i = 0; i < squareLists.size(); i++) {
            if (squareLists.get(i).getDangerHole() == 1  && squareLists.get(i).getDangerWumpus() == 1) {
                if(squareLists.get(i).getCount() == 2)
                {
                    Log.i("DOUBLE SET SAFE",String.valueOf(squareLists.get(i)));
                    squareLists.get(i).setSafe(true);
                }
            }
            if(squareLists.get(i).isStench())
            {
                if(checkmoveLeft(i) && checkmoveRight(i) && checkmoveUp(i))
                {
                    if((squareLists.get(i-1).isSafe() || squareLists.get(i-1).isVisited()) && (squareLists.get(i+1).isSafe() || squareLists.get(i-1).isVisited())
                            && (squareLists.get(i-SIZE).isSafe() || squareLists.get(i-SIZE).isVisited()))
                    {
                        WUMPUSISDIE = true;
                    }
                }
                if(checkmoveLeft(i) && checkmoveRight(i) && checkmoveDown(i)){
                    if((squareLists.get(i-1).isSafe() || squareLists.get(i-1).isVisited()) && (squareLists.get(i+1).isSafe() || squareLists.get(i+1).isVisited())
                            && (squareLists.get(i+SIZE).isSafe() || squareLists.get(i+SIZE).isVisited()))
                    {
                        WUMPUSISDIE = true;
                    }
                }
                if(checkmoveLeft(i) && checkmoveUp(i) && checkmoveDown(i)){
                    if((squareLists.get(i-1).isSafe() || squareLists.get(i-1).isVisited()) && (squareLists.get(i+SIZE).isSafe() || squareLists.get(i+SIZE).isVisited())
                            && (squareLists.get(i-SIZE).isSafe() || squareLists.get(i-SIZE).isVisited()))
                    {
                        WUMPUSISDIE = true;
                    }
                }
                if(checkmoveRight(i) && checkmoveUp(i) && checkmoveDown(i))
                {
                    if((squareLists.get(i+1).isSafe() || squareLists.get(i+1).isVisited()) && (squareLists.get(i+SIZE).isSafe() || squareLists.get(i+SIZE).isVisited())
                            && (squareLists.get(i-SIZE).isSafe() || squareLists.get(i-SIZE).isVisited()))
                    {
                        WUMPUSISDIE = true;
                    }
                }

            }
            if (squareLists.get(i).getDangerWumpus() > 1)
            {
                if(squareLists.get(i).getDangerWumpus() == 2)
                {
                    if(checkmoveLeft(i) && checkmoveDown(i))
                    {
                        if(squareLists.get(i - 1).isStench() && squareLists.get(i+SIZE).isStench())
                        {
                            if(squareLists.get(i-1+SIZE).isSafe() || squareLists.get(i-1+SIZE).isVisited() || squareLists.get(i-1+SIZE).getDangerHole()>2)
                            {
                                Log.i("Phat hien Wumpus","Shot");
                                WUMPUSISDIE = true;
                            }
                        }
                    }
                    if(checkmoveLeft(i) && checkmoveUp(i))
                    {
                        if(squareLists.get(i - 1).isStench() && squareLists.get(i-SIZE).isStench())
                        {
                            if(squareLists.get(i-1-SIZE).isSafe() || squareLists.get(i-1-SIZE).isVisited() || squareLists.get(i-1-SIZE).getDangerHole()>2)
                            {
                                Log.i("Phat hien Wumpus","Shot");
                                WUMPUSISDIE = true;
                            }
                        }
                    }
                    if(checkmoveUp(i) && checkmoveRight(i))
                    {
                        if(squareLists.get(i + 1).isStench() && squareLists.get(i-SIZE).isStench())
                        {
                            if(squareLists.get(i+1-SIZE).isSafe() || squareLists.get(i+1-SIZE).isVisited()|| squareLists.get(i+1-SIZE).getDangerHole()>2)
                            {
                                Log.i("Phat hien Wumpus","Shot");
                                WUMPUSISDIE = true;
                            }
                        }
                    }
                    if(checkmoveRight(i) && checkmoveDown(i))
                    {
                        if(squareLists.get(i + 1).isStench() && squareLists.get(i+SIZE).isStench())
                        {
                            if(squareLists.get(i+1+SIZE).isSafe() || squareLists.get(i+1+SIZE).isVisited()|| squareLists.get(i+1+SIZE).getDangerHole()>2)
                            {
                                Log.i("Phat hien Wumpus","Shot");
                                WUMPUSISDIE = true;
                            }
                        }
                    }
                }
                if(squareLists.get(i).getDangerWumpus() >2)
                {
                    squareLists.get(i).setSafe(false);
                }
            }
            if (squareLists.get(i).getDangerHole()>2)
            {
                squareLists.get(i).setSafe(false);
            }
        }
    }
    private void tickwhenAgentnovisite(int i)
    {
        if(squareLists.get(i).isBreeze() == false && squareLists.get(i).isStench() == false) // nếu không có gió và mùi
        {
            if(checkmoveLeft(i))
                squareLists.get(i-1).setSafe(true);
            if(checkmoveRight(i))
                squareLists.get(i+1).setSafe(true);
            if(checkmoveUp(i))
                squareLists.get(i-SIZE).setSafe(true);
            if(checkmoveDown(i))
                squareLists.get(i+SIZE).setSafe(true);
        }
        if(squareLists.get(i).isBreeze() == true && squareLists.get(i).isStench() == true) // nếu có gió và mùi
        {
            if(checkmoveLeft(i) && !squareLists.get(i-1).isSafe()&& !squareLists.get(i-1).isVisited())
            {
                squareLists.get(i-1).setDangerHole();
                squareLists.get(i-1).setDangerWumpus();
                squareLists.get(i-1).setCount();
                //Log.i("TAG","Count Left Breeze and Stench");
            }
            if(checkmoveRight(i)  && !squareLists.get(i+1).isSafe()&& !squareLists.get(i+1).isVisited())
            {
                squareLists.get(i+1).setDangerHole();
                squareLists.get(i+1).setDangerWumpus();
                squareLists.get(i+1).setCount();
                //Log.i("TAG","Count RIGHT Breeze and Stench");
            }
            if(checkmoveUp(i)  && !squareLists.get(i-SIZE).isSafe()&& !squareLists.get(i-SIZE).isVisited())
            {
                squareLists.get(i-SIZE).setDangerHole();
                squareLists.get(i-SIZE).setDangerWumpus();
                squareLists.get(i-SIZE).setCount();
                //Log.i("TAG","Count UP Breeze and Stench");
            }
            if(checkmoveDown(i) && !squareLists.get(i+SIZE).isSafe()&& !squareLists.get(i+SIZE).isVisited())
            {
                squareLists.get(i+SIZE).setDangerHole();
                squareLists.get(i+SIZE).setDangerWumpus();
                squareLists.get(i+SIZE).setCount();
                //Log.i("TAG","Count DOWN Breeze and Stench");
            }
        }
        if(squareLists.get(i).isBreeze() == true && !squareLists.get(i).isStench())// nếu có gió thi các ô xung quanh nguy hiểm gió +1( trừ những ô đã xác định an toàn)
            {
            if(checkmoveLeft(i) && !squareLists.get(i-1).isSafe() && !squareLists.get(i-1).isVisited())
            {
                squareLists.get(i-1).setDangerHole();
                squareLists.get(i-1).setCount();
                //Log.i("TAG","Count LEFT Breeze");
            }
            if(checkmoveRight(i)  && !squareLists.get(i+1).isSafe() && !squareLists.get(i+1).isVisited())
            {
                squareLists.get(i+1).setDangerHole();
                squareLists.get(i+1).setCount();
                //Log.i("TAG","Count RIGHT Breeze");
            }
            if(checkmoveUp(i)  && !squareLists.get(i-SIZE).isSafe() && !squareLists.get(i-SIZE).isVisited())
            {
                squareLists.get(i-SIZE).setDangerHole();
                squareLists.get(i-SIZE).setCount();
                //Log.i("TAG","Count UP Breeze");
            }
            if(checkmoveDown(i) && !squareLists.get(i+SIZE).isSafe()&& !squareLists.get(i+SIZE).isVisited())
            {
                squareLists.get(i+SIZE).setDangerHole();
                squareLists.get(i+SIZE).setCount();
                //Log.i("TAG","Count DOWN Breeze");
            }

        }
        if(squareLists.get(i).isStench() == true && !squareLists.get(i).isBreeze()) //nếu có mùi thì các ô xung quanh có nguy hiểm mùi+1 ( trừ những ô đã xác định an toàn)
        {
            if(checkmoveLeft(i) && !squareLists.get(i-1).isSafe() && !squareLists.get(i-1).isVisited())
            {
                squareLists.get(i-1).setDangerWumpus();
                squareLists.get(i-1).setCount();
                //Log.i("TAG","Count LEFT Stench");
            }
            if(checkmoveRight(i)  && !squareLists.get(i+1).isSafe()&& !squareLists.get(i+1).isVisited())
            {
                squareLists.get(i+1).setDangerWumpus();
                squareLists.get(i+1).setCount();
                //Log.i("TAG","Count RIGHT Stench");
            }
            if(checkmoveUp(i) && !squareLists.get(i-SIZE).isSafe()&& !squareLists.get(i-SIZE).isVisited())
            {
                squareLists.get(i-SIZE).setDangerWumpus();
                squareLists.get(i-SIZE).setCount();
                //Log.i("TAG","Count UP Stench");
            }
            if(checkmoveDown(i)  && !squareLists.get(i+SIZE).isSafe()&& !squareLists.get(i+SIZE).isVisited())
            {
                squareLists.get(i+SIZE).setDangerWumpus();
                squareLists.get(i+SIZE).setCount();
                //Log.i("TAG","Count DOWN Stench");
            }
        }
    }
    private void setWhenWumpusdie ()
    {
        for(int i = 0; i<squareLists.size();i++)
        {
            if(squareLists.get(i).isWumpus())
            {
                squareLists.get(i).setWumpus(false);
                squareLists.get(i).setSafe(true);
                if(squareLists.get(i).isBreeze())
                {
                    imageViewList.get(i).setBackgroundResource(R.drawable.bgwind);
                    imageViewList.get(i).setImageResource(android.R.color.transparent);
                }
                else
                    imageViewList.get(i).setImageResource(R.drawable.background);
                if(checkmoveRight(i))
                {
                    squareLists.get(i+1).setWhenWumpusdie();
                    squareLists.get(i+1).setStench(false);
                    setAllStatus(i+1);
                }
                if(checkmoveLeft(i))
                {
                    squareLists.get(i-1).setWhenWumpusdie();
                    squareLists.get(i-1).setStench(false);
                    setAllStatus(i-1);
                }
                if(checkmoveDown(i))
                {
                    squareLists.get(i+SIZE).setWhenWumpusdie();
                    squareLists.get(i+SIZE).setStench(false);
                    setAllStatus(i+SIZE);
                }
                if(checkmoveUp(i))
                {
                    squareLists.get(i-SIZE).setWhenWumpusdie();
                    squareLists.get(i-SIZE).setStench(false);
                    setAllStatus(i-SIZE);
                }
                break;
            }
        }
    }
    private void checkAgentdie(Square square, int i)
    {
        if(square.isAgent() && (square.isHole()||square.isWumpus()))
        {
            btnDown.setEnabled(false);
            btnUp.setEnabled(false);
            btnLeft.setEnabled(false);
            btnRight.setEnabled(false);
            btnAuto.setEnabled(false);
            POSITIONWHENAGENTDIE = i;
            imageViewList.get(i).setImageResource(R.drawable.gameover);
        }
    }
    private void rulesofGame()
    {
        for(int i = 0; i < squareLists.size() ; i++)
        {
            if(squareLists.get(i).isAgent())
            {
                if(checkmoveLeft(i))
                {
                    if(squareLists.get(i-1).isHole() || squareLists.get(i-1).isWumpus() || squareLists.get(i-1).isGold())
                    {
                        recreate();
                    }
                }
                if(checkmoveRight(i))
                {
                    if(squareLists.get(i+1).isHole() || squareLists.get(i+1).isWumpus() || squareLists.get(i+1).isGold())
                    {
                        recreate();
                    }
                }
                if(checkmoveUp(i))
                {
                    if(squareLists.get(i-SIZE).isHole() || squareLists.get(i-SIZE).isWumpus() || squareLists.get(i-SIZE).isGold())
                    {
                        recreate();
                    }
                }
                if(checkmoveDown(i))
                {
                    if(squareLists.get(i+SIZE).isHole() || squareLists.get(i+SIZE).isWumpus() || squareLists.get(i+SIZE).isGold())
                    {
                        recreate();
                    }
                }
            }
        }
    }
    private void setDisableImageViewList()
    {
        for(int i = 0; i < imageViewList.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {

            }
            if((squareLists.get(i).isGold() || squareLists.get(i).isHole() || squareLists.get(i).isWumpus()) && i != POSITIONWHENAGENTDIE)
            {
                imageViewList.get(i).setImageBitmap(null);
                imageViewList.get(i).setVisibility(View.INVISIBLE);
            }
            if(i == POSITIONWHENAGENTDIE && POSITIONWHENAGENTDIE!= 0)
            {
                imageViewList.get(i).setImageResource(R.drawable.gameover);
            }
        }
    }
    private void setEnableImageViewList()
    {
        for (int i = 0; i < imageViewList.size(); i++)
        {
            if(squareLists.get(i).isGold())
            {
                imageViewList.get(i).setImageResource(R.drawable.gold);
                imageViewList.get(i).setVisibility(View.VISIBLE);
            }
            if(squareLists.get(i).isWumpus() && i != POSITIONWHENAGENTDIE)
            {
                imageViewList.get(i).setImageResource(R.drawable.wumpus);
                imageViewList.get(i).setVisibility(View.VISIBLE);
            }
            if(squareLists.get(i).isHole() && i != POSITIONWHENAGENTDIE)
            {
                imageViewList.get(i).setImageResource(R.drawable.hole);
                imageViewList.get(i).setVisibility(View.VISIBLE);
            }
            if(i == POSITIONWHENAGENTDIE && POSITIONWHENAGENTDIE != 0)
            {
                imageViewList.get(i).setImageResource(R.drawable.gameover);
            }
        }
    }
    private void setVisibleImageViewList()
    {
        for (int i = 0; i < imageViewList.size(); i++)
        {
            if(squareLists.get(i).isAgent())
            {

            }
            else
            {
                 imageViewList.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }
}
