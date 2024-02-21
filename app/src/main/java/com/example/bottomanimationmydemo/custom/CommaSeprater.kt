package com.example.bottomanimationmydemo.custom

class CommaSeprater {
    companion object{
        fun commaSeparatedString(geeklist:ArrayList<String>) : String{
            val str = StringBuilder("")

            // Traversing the ArrayList

            // Traversing the ArrayList
            for (eachstring in geeklist) {

                // Each element in ArrayList is appended
                // followed by comma
                str.append(eachstring).append(",")
            }

            // StringBuffer to String conversion

            // StringBuffer to String conversion
            var commaseparatedlist = str.toString()

            // Condition check to remove the last comma

            // Condition check to remove the last comma
            if (commaseparatedlist.length > 0) commaseparatedlist = commaseparatedlist.substring(
                0, commaseparatedlist.length - 1
            )

            // Printing the comma separated string

            // Printing the comma separated string
            println(commaseparatedlist)
            return commaseparatedlist
        }
        fun commaSeparatedNumber(geeklist:ArrayList<Int>) : String {
            val str = StringBuilder("")

            // Traversing the ArrayList

            // Traversing the ArrayList
            for (eachstring in geeklist) {

                // Each element in ArrayList is appended
                // followed by comma
                str.append(eachstring).append(",")
            }

            // StringBuffer to String conversion

            // StringBuffer to String conversion
            var commaseparatedlist = str.toString()

            // Condition check to remove the last comma

            // Condition check to remove the last comma
            if (commaseparatedlist.length > 0) commaseparatedlist = commaseparatedlist.substring(
                0, commaseparatedlist.length - 1
            )

            // Printing the comma separated string

            // Printing the comma separated string
            println(commaseparatedlist)
            return commaseparatedlist
        }

    }
}