# RandomAccess

- This interface is a member of the Java Collections Framework.

- Marker interface used by List implementations to indicate that they support fast (generally constant time) random access. 

- The primary purpose of this interface is to allow generic algorithms to alter their behavior to provide good performance when applied to either random or sequential access lists.

- Generic list algorithms are encouraged to check whether the given list is an instanceof this interface before applying an algorithm that would provide poor performance if it were applied to a sequential access list(such as LinkedList).

- As a rule of thumb, a List implementation should implement this interface if, for typical instances of the class, this loop:
    ```
    for (int i=0, n=list.size(); i < n; i++)
             list.get(i);
    
    ```
    runs faster than this loop:
    ```
    for (Iterator i=list.iterator(); i.hasNext(); )
             i.next();

    ```
  
- other  
    - LinkedList 底层实现是双向链表， LinkedList没有实现RandomAccess接口。 ArrayList实现了该接口
    - 在遍历集合前，我们便可以通过 instanceof 做判断， 选择合适的集合遍历方式，当数据量很大时， 就能大大提升性能
    - 随机访问列表(实现了RandomAccess)使用循环遍历，顺序访问列表使用迭代器遍历。
        ```
        public static void traverse(List list){
        
                if (list instanceof RandomAccess){
                    System.out.println("实现了RandomAccess接口，不使用迭代器");
        
                    for (int i = 0;i < list.size();i++){
                        System.out.println(list.get(i));
                    }
        
                }else{
                    System.out.println("没实现RandomAccess接口，使用迭代器");
        
                    Iterator it = list.iterator();
                    while(it.hasNext()){
                        System.out.println(it.next());
                    }
        
                }
            }
        }

        ```
        
    - 数组支持随机访问， 查询速度快， 增删元素慢； 链表支持顺序访问， 查询速度慢， 增删元素快。所以对应的 ArrayList 查询速度快，LinkedList 查询速度慢， RandomAccess 这个标记接口就是标记能够随机访问元素的集合， 简单来说就是底层是数组实现的集合
    - ArrayList使用for循环遍历优于迭代器遍历LinkedList使用迭代器遍历优于for循环遍历