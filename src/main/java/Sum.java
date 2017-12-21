import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class Sum {

    public static class SumMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
        //input: user:movie \t score
        //output user:movie \t score
        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = value.toString().trim().split("\t");
            double score = Double.parseDouble(tokens[1]);
            context.write(new Text(tokens[0]), new DoubleWritable(score));
        }
    }

    public static class SumReducer extends Reducer<Text, DoubleWritable, IntWritable, Text> {

        @Override
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {

            double threshold = 1;

            double sum = 0;
            while(values.iterator().hasNext()){
                sum += values.iterator().next().get();
            }

            if(sum >= threshold){
                String[] tokens = key.toString().trim().split(":");
                int user = Integer.parseInt(tokens[0]);
                context.write(new IntWritable(user), new Text(tokens[1] + ":" + sum));
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setMapperClass(SumMapper.class);
        job.setReducerClass(SumReducer.class);
        job.setJarByClass(Sum.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        TextInputFormat.setInputPaths(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}
