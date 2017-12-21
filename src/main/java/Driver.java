
public class Driver {

    public static void main(String[] args) throws Exception{

        /*

            args0: original dataset
            args1: output directory for DividerByUser job
            args2: output directory for coOccurrenceMatrixBuilder job
            args3: output directory for Normalize job
            args4: output directory for Multiplication job
            args5: output directory for Sum job

         */

        DataDividerByUser dataDividerByUser = new DataDividerByUser();
        CoOccurrenceMatrixGenerator coOccurrenceMatrixGenerator = new CoOccurrenceMatrixGenerator();
        Normalize normalize = new Normalize();
        Multiplication multiplication = new Multiplication();
        Sum sum = new Sum();

        String rawInput = args[0];
        String userMovieListDir = args[1];
        String coOccurrenceMatrixDir = args[2];
        String normalizeDir = args[3];
        String multiplicationDir = args[4];
        String sumDir = args[5];

        String[] path1 = {rawInput, userMovieListDir};
        String[] path2 = {userMovieListDir, coOccurrenceMatrixDir};
        String[] path3 = {coOccurrenceMatrixDir, normalizeDir};
        String[] path4 = {normalizeDir, multiplicationDir};
        String[] path5 = {multiplicationDir, sumDir};

        dataDividerByUser.main(path1);
        coOccurrenceMatrixGenerator.main(path2);
        normalize.main(path3);
        multiplication.main(path4);
        sum.main(path5);

    }
}
