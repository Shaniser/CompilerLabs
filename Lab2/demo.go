package main

import (
	"fmt"
	"go/ast"
	"go/format"
	"go/parser"
	"go/types"

	//"go/printer"
	"go/token"
	"os"
)

func insertKomma(file *ast.File) {
	ast.Inspect(file, func(node ast.Node) bool {
		if s, ok := node.(*ast.CallExpr); ok {
			if types.ExprString(s.Fun) == "fmt.Println" {
				var argArr []ast.Expr
				for i, expr := range s.Args {
					argArr = append(argArr, expr.(*ast.BasicLit))
					if i < len(s.Args) - 1 {
						argArr = append(argArr, &ast.BasicLit{
							Value: "\", \"",
							Kind: token.STRING,
						})
					}
				}
				s.Args = argArr
			}
		}

		return true
	})
}

func main() {
	if len(os.Args) != 2 {
		return
	}

	fset := token.NewFileSet()
	if file, err := parser.ParseFile(fset, os.Args[1], nil, parser.ParseComments); err == nil {
		insertKomma(file)

		if format.Node(os.Stdout, fset, file) != nil {
			fmt.Printf("Formatter error: %v\n", err)
		}
	} else {
		fmt.Printf("Errors in %s\n", os.Args[1])
	}
}
