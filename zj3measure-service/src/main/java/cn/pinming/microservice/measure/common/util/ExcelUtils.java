package cn.pinming.microservice.measure.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class ExcelUtils {

    public static void export(HttpServletResponse response, List list, Class clazz, String name) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).autoCloseStream(Boolean.FALSE).sheet(name).doWrite(list);
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "导出文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void download(HttpServletResponse response,String exportFilePath, Map data,String name) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Map.class).withTemplate(exportFilePath).sheet().doFill(data);
    }

    /**
     * 拷贝行
     *
     * @param workbook
     * @param sheet
     * @param addRowNum
     * @param typeRow
     * @param fromRow
     * @return
     */
    public static int getRowNum(Workbook workbook, Sheet sheet, int addRowNum, int typeRow, Row fromRow) {
        int rowNum = typeRow;
        for (int i = 0; i < addRowNum; i++) {
            rowNum++;
            Row toRow = sheet.createRow(rowNum);
            copyRow(workbook, fromRow, toRow, true);
        }
        return rowNum;
    }

    /**
     * 行复制功能
     *
     * @param wb            工作簿
     * @param fromRow       从哪行开始
     * @param toRow         目标行
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyRow(Workbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {
        toRow.setHeight(fromRow.getHeight());
        for (Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            Cell tmpCell = cellIt.next();
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell, copyValueFlag);
        }
        Sheet worksheet = fromRow.getSheet();
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(),
                        (toRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                        cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
                worksheet.addMergedRegionUnsafe(newCellRangeAddress);
            }
        }
    }

    public static void copyCell(Workbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {
        CellStyle newStyle = wb.createCellStyle();
        CellStyle srcStyle = srcCell.getCellStyle();
        newStyle.cloneStyleFrom(srcStyle);
        newStyle.setFont(wb.getFontAt(srcStyle.getFontIndexAsInt()));
        // 样式
        distCell.setCellStyle(newStyle);
        // 内容
        if (srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType srcCellType = srcCell.getCellType();
        distCell.setCellType(srcCellType);
        if (copyValueFlag) {
            if (srcCellType == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(srcCell)) {
                    distCell.setCellValue(srcCell.getDateCellValue());
                } else {
                    distCell.setCellValue(srcCell.getNumericCellValue());
                }
            } else if (srcCellType == CellType.STRING) {
                distCell.setCellValue(srcCell.getRichStringCellValue());
            } else if (srcCellType == CellType.BLANK) {

            } else if (srcCellType == CellType.BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());
            } else if (srcCellType == CellType.ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());
            } else if (srcCellType == CellType.FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());
            }
        }
    }

    public static void convertDataToRow(Workbook workbook,String data, Row row, int cellNum) {
        convertDataToRowWithBgColor(workbook,data, row, cellNum, false ,(byte)1);
    }

    public static void convertDataToRowWithBgColor(Workbook workbook,String data, Row row, int cellNum, boolean colorFlag,Byte isCorrect) {
        Cell cell = row.createCell(cellNum++);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        if (cellStyle != null) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setWrapText(true);
            if (colorFlag) {
                cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            } else {
                cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            }
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 9);
            font.setColor(Font.COLOR_NORMAL);
            if (Objects.equals(isCorrect, (byte) 2)) {
                font.setUnderline(Font.U_SINGLE);
            }
            cellStyle.setFont(font);
        }
        cell.setCellStyle(cellStyle);
        cell.setCellValue(null == data ? "" : data);
    }

    public static void mergeCellData(Sheet sheet, int regionRow, int rowSize) {
        int col = 1;
        while (true) {
            if (col == 8) {
                break;
            }
            if (col == 4) {
                col = 5;
            }
            CellRangeAddress region = new CellRangeAddress(regionRow, regionRow + rowSize, col, col);
            sheet.addMergedRegion(region);
            col++;
        }
    }


    public static void mergeCellReboundData(Sheet sheet, int baseRow, int rowNum) {
        int firstRow = baseRow;
        int lastRow = baseRow + 1;
        int colNum = 0;
        while (true) {
            if (lastRow == rowNum && colNum > 12) {
                break;
            }
            if (colNum == 2 ) {
                colNum = 10;
            }
            if (colNum == 13) {
                colNum = 0;
                firstRow = firstRow + 2;
                lastRow = lastRow + 2;
            }
            CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, colNum, colNum);
            sheet.addMergedRegion(region);
            colNum++;
        }
    }


    public static void fillRowPointValues(Workbook workbook ,Row row1, List<List<String>> pointValueList, int i2) {
        List<String> pointValues1 = pointValueList.get(i2);
        int s1 = 2;
        for (String s : pointValues1) {
            convertDataToRow(workbook, s, row1, s1);
            s1++;
        }
    }

}
