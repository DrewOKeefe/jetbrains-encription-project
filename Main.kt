package encryptdecrypt
import java.io.File

fun main(args: Array<String>) {
    var alg = "shift"
    var mode = "enc"
    var data = ""
    var key = 0
    var inFile = ""
    var outFile = ""
    var output = ""

// DEFINING ABOVE VARIABLES:
    for (arg in args.indices step 2) {
        when (args[arg]) {
            "-alg" -> alg = args[arg + 1]
            "-mode" -> mode = args[arg + 1]
            "-key" -> key = args[arg + 1].toInt()
            "-data" -> data = args[arg + 1]
            "-in" -> {
                inFile = args[arg + 1]
                if (args.contains("-data")) continue else data = File(inFile).readText()
            }
            "-out" -> outFile = args[arg + 1]
            else -> continue
        }
    }

// FUNCTIONS TO TRANSFORM DATA
    fun unicode(a: String): String {
        for (i in a) {
            output += if (mode == "enc" || mode == "") (i + key) else (i - key)
        }
        return output
    }
    fun shift(a: String): String {
        for (i in a) {
            val holder: Char
            val range =
                if (i in 'a'..'z') 'a'..'z'
                else if (i in 'A'..'Z') 'A'..'Z'
                else {
                    output += i
                    continue
                }
            if (i in range) {
                if (mode == "enc") {
                    holder = i + key
                    output += if (holder in range) holder else holder - 26
                } else {
                    holder = i - key
                    output += if (holder in range) holder else holder + 26
                }
            }
        }
        return output
    }

// PROCESSES DATA BASED ON "SHIFT" OR "UNICODE"
    if (alg == "unicode") unicode(data)
    if (alg == "shift") shift(data)

// CHOOSES WHERE "OUTPUT" GETS PRINTED:
    if (args.contains("-out")) File(outFile).writeText(output) else print(output)
}
