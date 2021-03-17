package main

import (
	"fmt"
	"go/ast"
	"go/parser"
	"go/token"
	"os"
	"reflect"
)

func main() {
	src := `
package p
const c = 1.0
var X = f(3.14)*2 + c
`
	fmt.Println("hello world")
	fset := token.NewFileSet() // positions are relative to fset
	f, err := parser.ParseFile(fset, "src.go", src, 0)
	if err != nil {
		panic(err)
	}
	ast.Fprint(os.Stdout, fset, f, func(name string, value reflect.Value) bool {
		if ast.NotNilFilter(name, value) {
			return value.Type().String() != "*ast.Object"
		}
		return false
	})
	fmt.Println("hello world","test")
	fmt.Println("hello world", "dada","test")
}
