import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

/**
 * 
  609. Find Duplicate File in System

Given a list of directory info including directory path, and all the
files with contents in this directory, you need to find out all the
groups of duplicate files in the file system in terms of their paths.

A group of duplicate files consists of at least two files that have
exactly the same content.

A single directory info string in the input list has the following format:

"root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"

It means there are n files (f1.txt, f2.txt ... fn.txt with content f1_content,
f2_content ... fn_content, respectively) in directory root/d1/d2/.../dm.
Note that n >= 1 and m >= 0. If m = 0, it means the directory is just
the root directory.

The output is a list of group of duplicate file paths. For each group, it
contains all the file paths of the files that have the same content. A
file path is a string that has the following format:

"directory_path/file_name.txt"

Example 1:

Input:
["root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"]
Output:  
[["root/a/2.txt","root/c/d/4.txt","root/4.txt"],["root/a/1.txt","root/c/3.txt"]]

Note:

    1. No order is required for the final output.
    2. You may assume the directory name, file name and file content only
    has letters and digits, and the length of file content is in the
    range of [1,50].
    3. The number of files given is in the range of [1,20000].
    4. You may assume no files or directories share the same name in a
    same directory.
    5. You may assume each given directory info represents a unique
    directory. Directory path and file infos are separated by a
    single blank space.

Follow up beyond contest:

    1. Imagine you are given a real file system, how will you search
    files? DFS or BFS ?
    2. If the file content is very large (GB level), how will you modify
    your solution?
    3. If you can only read the file by 1kb each time, how will you modify
    your solution?
    4. What is the time complexity of your modified solution? What is the
    most time consuming part and memory consuming part of it? How to
    optimize?
    5. How to make sure the duplicated files you find are not false positive?

 */

public class Solution {

  public List<List<String>> findDuplicate(String[] paths) {
    HashMap<String, List<String>> map = createContentMap(paths);
    List<List<String>> list = new ArrayList<List<String>>();
    // iterate over all the keys and insert the value into the list
    Iterator<String> it = map.keySet().iterator();
    while(it.hasNext()) {
      String key = it.next();
      //System.out.println("key:" + key);
      List<String> value = map.get(key);
      if(value.size() > 1) {
        list.add(value);
      }
    }
    return list;
  }
  
  public HashMap<String, List<String>> createContentMap(String[] paths) {
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();

    for(int i = 0; i < paths.length; i++) {
      String record =  paths[i]; // "root/a 1.txt(abcd) 2.txt(efgh)"
      int spaceIndex = record.indexOf(' ');
      String dirName = record.substring(0, spaceIndex);
      record = record.substring(spaceIndex).trim();
      while(record.length() > 0) {
        spaceIndex = record.indexOf(' ');
        String fileInfo = "";
        if(spaceIndex >= 0) {
          fileInfo = record.substring(0, spaceIndex);
          record = record.substring(spaceIndex).trim();
        } else {
          fileInfo = record;
          record = "";
        }
        int k = fileInfo.indexOf('(');
        String fileName = fileInfo.substring(0, k);
        //System.out.println("fileName:" + fileName);
        String fileContent = fileInfo.substring(k+1, fileInfo.length()-1);
        if(!map.containsKey(fileContent)) {
          map.put(fileContent, new ArrayList<String>());
        }
        map.get(fileContent).add(dirName + "/" + fileName);
      }
    }
    //java.util.Iterator<String> it = map.keySet().iterator();
    //while(it.hasNext()) {
    //  System.out.println("key: " + it.next());
    //}
    return map;
  }

  public static void main(String[] args) {
    Solution s = new Solution();
    String[] paths = {"root/a 1.txt(abcd) 2.txt(efgh)",
                      "root/c 3.txt(abcd)",
                      "root/c/d 4.txt(efgh)",
                      "root 4.txt(efgh)"};
    String[] paths2 = {"root/a 1.txt(abcd) 2.txt(efsfgh)",
                       "root/c 3.txt(abdfcd)",
                       "root/c/d 4.txt(efggdfh)"
    };
        
        
    List<List<String>> list = s.findDuplicate(paths);
    Iterator<List<String>> listIt = list.iterator();
    while(listIt.hasNext()) {
      List<String> fileNameList = listIt.next();
      Iterator<String> fileNameIt = fileNameList.iterator();
      while(fileNameIt.hasNext()) {
        String fileName = fileNameIt.next();
        System.out.print(" " + fileName);
      }
      System.out.println("");
    }
  }
}
