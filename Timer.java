package vl.com.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Timer 
{
	List<Bdaydtls> bdaylist=new ArrayList<Bdaydtls>();
	Main t=new Main();
	public void readdtls(String file) throws IOException, ParseException
	{
		BufferedReader br=new BufferedReader(new FileReader(file));
		String record;
		while((record=br.readLine())!=null)
		{
			StringTokenizer str=new StringTokenizer(record.trim(), ">");
			while(str.hasMoreTokens())
			{
				Bdaydtls bdtls=new Bdaydtls();
				String n=str.nextToken();
				bdtls.setName(n);
				String n1=str.nextToken();
				bdtls.setDate(Timestamp.valueOf(n1));;
				bdaylist.add(bdtls);
			}
		}
		br.close();
		Collections.sort(bdaylist,new Compare());
		for(Bdaydtls b:bdaylist)
			System.out.println(b.name+"---"+b.date);
	}
	@SuppressWarnings("deprecation")
	public void generatealert()
	{
		int i=0;
		List<Bdaydtls> newbdaylist=new ArrayList<Bdaydtls>();
		for(int j=0;j<bdaylist.size();j++)
		{
			Timestamp curntime=new Timestamp(System.currentTimeMillis());
			Timestamp ntime=new Timestamp(curntime.getYear(), bdaylist.get(j).date.getMonth(),bdaylist.get(j).date.getDate(),bdaylist.get(j).date.getHours(),bdaylist.get(j).date.getMinutes(),bdaylist.get(j).date.getSeconds(),bdaylist.get(j).date.getNanos());
			Bdaydtls bd=new Bdaydtls();
			bd.setName(bdaylist.get(j).name);
			bd.setDate(ntime);
			newbdaylist.add(bd);
		}
		Collections.sort(newbdaylist,new Compare());
		for(Bdaydtls b:newbdaylist)
			System.out.println(b.name+"---"+b.date);
		while(true)
		{
			if(i==newbdaylist.size())
				i=0;
			long time=newbdaylist.get(i).date.getTime()-System.currentTimeMillis();
			if(time<0){}
			else
			{
				synchronized (t) 
				{
					try {
						System.out.println("wating for next bday");
						t.wait(time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(newbdaylist.get(i).name+"  bday occured now");
				}
			}
			i++;
		}
	}
}
class Compare implements Comparator<Bdaydtls>
{
	@Override
	public int compare(Bdaydtls o1, Bdaydtls o2) {
		return o1.date.compareTo(o2.date);
	}
}