import java.io.BufferedReader;

import javax.xml.soap.Text;

//package KNNMapReduce;

public class KNNMapReduce {
	
	//class KNNMapper for processing map step

	public static class KNNMapper extends Mapper<Object, Text, Text, IntWritable>
	{	
		//the setup function is run once pre-processing data(get test set)
		public void setup(Context context)throws IOException
		{
			String ln = context.getCacheFiles()[0].getFragment().toString();//[0]??
			BufferedReader br = new BufferedRedaer(new FileReader(new FileReader(localname)));//localname??
			int count = 0//keep records of number of line readin so far
			while(br!=null){
				
				//add data to data structure
				br.readLine();
				count++;
			}
			br.close();
		}
		//perform map step
		public void map(Object key, Text value, Context context)throws IOException, InteruptedException
		{
			//implement
		}
	}
	//---------------------END MAP------------------------
	public static class KNNReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		public void reduce(Text key, Iterable<IntWritable> values, Context cotext)throws IOException, InteruptedException
		{
			//implement
		}
	}
	//--------------------END REDUCE----------------------
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "k-nearest neighbour");
		job.setJarByClass(KNNMapReduce.class);
		job.setMapperClass(KNNMapper.class);
		job.setCombinerClass(KNNReducer.class);
		job.setReducerClass(KNNReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//sending parameters to MR
		//1. training set(path in HDFS)
		//2. path for result
		//3. test set
		//4. number of neighbours(k) for vote
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.addCacheFile(new URI(args[2]));//e.g. "/home/bwi/cache/file1.txt#first"
		conf.set("K", args[3]); //the number of k-nearest 
		job.waitForCompleion(true);
		Counters counter = job.getCounters();
		System.out.println("Input Records: "+counters.findCounter(TaskCounter.MAP_INPUT_RECORDS).getValue());
	}
}
