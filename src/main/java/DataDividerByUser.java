import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

public class DataDividerByUser {
    public static class DataDividerMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

        // input: user, movie, rating eg. 1, 1, 5
        // output: u1, m1:3
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] val = value.toString().trim().split(",");
            int user = Integer.parseInt(val[0]);
            String movie = val[1];
            String rating = val[2];
            context.write(new IntWritable(user), new Text(movie + ":" + rating));

        }
    }

    public static class DataDividerReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

        // input: 1, 1:5
        // output: key:1, value: 1:5, 2:3...
        public void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            StringBuilder sb = new StringBuilder();
            while(values.iterator().hasNext()){
                sb.append("," + values.iterator().next());
            }
            context.write(key, new Text(sb.toString().replace(",", "")));
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);
        job.setMapperClass(DataDividerMapper.class);
        job.setOutputKeyClass(DataDividerReducer.class);

        job.setJarByClass(DataDividerByUser.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);

        TextInputFormat.setInputPaths(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }
}
