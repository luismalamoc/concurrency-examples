package main

import (
	"fmt"
	"log"
	"net/http"
	"os"
	"sync"
)

var wg sync.WaitGroup

func sendRequest(url string) {
	res, err := http.Get(url)
	if err != nil {
		panic(err)
	}
	defer wg.Done()
	fmt.Printf("[%d] %s\n", res.StatusCode, url)
}

func main() {
	if len(os.Args) < 2 {
		log.Fatalln("Usage: go run main.go <url1> <url2> .. <urln>")
	}

	for _, url := range os.Args[1:] {
		go sendRequest("https://" + url)
		wg.Add(1)
	}

	wg.Wait()
}
