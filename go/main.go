package main

import (
	"fmt"
	"net/http"
	"runtime"
)

func helloHandler(w http.ResponseWriter, r *http.Request) {
     goVersion := runtime.Version()
	response := fmt.Sprintf("Hello, World!\nGo Version: %s\n", goVersion)
	fmt.Fprint(w, response)
}

func main() {
	http.HandleFunc("/", helloHandler)

	fmt.Println("Server listening on port 8080...")
	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		fmt.Println("Error starting the server:", err)
	}
}
