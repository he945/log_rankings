
import java.io.File

//val uniqueHits: Map<String, Int> = HashMap()
val uniqueHits = mutableMapOf<String, Int>()
val userEntries = mutableMapOf<String, MutableSet<String>>()
val userCounts = mutableMapOf<String, Int>()
val userUniquePageViews = mutableMapOf<String, MutableSet<String>>()
val userUniquePageViewCounts = mutableMapOf<String, Int>()

fun parseLine(line:String) {
  val columns = line.split(",")
  if (columns.size == 3) {
    val columnOne = columns[0]
    val columnTwo = columns[1]
    val columnThree = columns[2]
    if (columnOne == "Path" && columnTwo == "User" && columnThree == "Timestamp") {
      return
    }
    else {
      val keyVal = columnOne
      if (uniqueHits.containsKey(keyVal)) {
        var count = uniqueHits[keyVal]
        if (count != null) {
          count += 1
          uniqueHits.put(keyVal, count)
          var setEntry = userEntries.get(keyVal)
          if (setEntry != null){
            setEntry.add(columnTwo)
            userEntries.put(keyVal, setEntry)
          }
        }
      }
      else {
        uniqueHits[keyVal] = 1
        val newSet = HashSet<String>()
        newSet.add(columnTwo)
        userEntries.put(keyVal, newSet)
      }
      val uniquePageView = columnOne + columnThree
      if(userUniquePageViews.containsKey(columnTwo)) {
        var setEntry = userUniquePageViews.get(columnTwo)
        if(setEntry != null) {
          setEntry.add(uniquePageView)
          userUniquePageViews.put(columnTwo, setEntry)
        }
      }
      else {
        val newSet = HashSet<String>()
        newSet.add(uniquePageView)
        userUniquePageViews.put(columnTwo, newSet)
      }
    }
  }
}
fun main(args: Array<String>) {
  if (args.size < 1) {
    println("usage: parse_log log.csv")
  }
  
  val fileName = args[0]

  File(fileName).forEachLine { parseLine(it) }
  val sortedUniqueHits = uniqueHits.toList().sortedBy { (_, value) -> value}.toMap()
  println("----------------------------------------")
  println("pages by unique hits")
  println("----------------------------------------")
  for (entry in sortedUniqueHits) {
      print("Page: " + entry.key)
      println(" Unique Hits: " + entry.value)
  }

  for (entry in userEntries) {
    userCounts.put(entry.key, entry.value.size)
  }
  val sortedUserCounts = userCounts.toList().sortedBy { (_, value) -> value}.toMap()
  println("\n----------------------------------------")
  println("pages by number of users")
  println("----------------------------------------")
  for (entry in sortedUserCounts) {
      print("Page: " + entry.key)
      println(" Unique Users: " + entry.value)
  }

  for (entry in userUniquePageViews) {
    userUniquePageViewCounts.put(entry.key, entry.value.size)
  }
  val sortedUserUniquePageViewCounts = userUniquePageViewCounts.toList().sortedBy { (_, value) -> value}.toMap()
  println("\n----------------------------------------")
  println("users by unique page views")
  println("----------------------------------------")
  for (entry in sortedUserUniquePageViewCounts) {
      print("User: " + entry.key)
      println(" Unique Page Views: " + entry.value)
  }      
}